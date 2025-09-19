package com.myproject.tool_rent_app.services;

import com.myproject.tool_rent_app.entities.*;
import com.myproject.tool_rent_app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private KardexTypeRepository kardexTypeRepository;

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private ToolService toolService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private KardexRepository kardexRepository;

    @Autowired
    private LoanStateRepository loanStateRepository;

    @Autowired
    private ToolStateRepository toolStateRepository;

    // Lista todos los préstamos
    public ArrayList<LoanEntity> getLoans() {
        return (ArrayList<LoanEntity>) loanRepository.findAll();
    }


    public LoanEntity getLoanById(Long id){
        return loanRepository.findById(id).get();
    }

    // RF2.1 Registrar un préstamo asociando cliente y herramienta, con fecha de entrega y
    // fecha pactada de devolución. Se actualiza el kardex.
    public LoanEntity registerLoan(LoanEntity loan) {
        String activeState = "Activo";
        String restrictedState = "Restringido";

        // Validación del estado 'Activo' del cliente
        String clientState = loan.getClient().getCurrentState().getName();
        if (!clientState.equals(activeState)) {
            throw new RuntimeException("El cliente no se encuentra '" +  activeState + "', no puede registrar un préstamo");
        }

        List<LoanEntity> previousLoans = loanRepository.findByClientId(loan.getClient().getId());

        // Verifica que el cliente no tenga más de 5 préstamos activos
        int count = 0;
        for (LoanEntity prevLoan: previousLoans) {
            if (prevLoan.getCurrentState().getName().equals("En progreso")) {
                count++;
            }
        }
        if (count >= 5) {
            throw new RuntimeException("El cliente ya tiene 5 préstamos activos");
        }

        // Verifica si el cliente esta al dia (no tiene prestamos atrasados o multas pendientes)
        for (LoanEntity prevLoan : previousLoans) {
            if (overdueFine(prevLoan.getId()).compareTo(BigDecimal.ZERO) > 0) {
                if (loan.getClient().getCurrentState().getName().equals(activeState)) {
                    // Si el cliente tiene deuda, actualiza su estado a restringido
                    clientService.changeClientState(loan.getClient().getId(), restrictedState);
                }
                throw new RuntimeException("El cliente tiene multas impagas por préstamos atrasados");
            }

            if (prevLoan.isDamaged()) {
                if (loan.getClient().getCurrentState().getName().equals(activeState)) {
                    // Si el cliente tiene multa, actualiza su estado a restringido
                    clientService.changeClientState(loan.getClient().getId(), restrictedState);
                }
                throw new RuntimeException("El cliente tiene una multa por reposición de herramienta dañada");
            }
        }

        // Verifica que no existan préstamos en curso para la misma herramienta
        List<LoanEntity> activeLoansForTool = loanRepository.findByToolIdAndCurrentStateName(loan.getTool().getId(), "En progreso");
        if (!activeLoansForTool.isEmpty()) {
            throw new RuntimeException("La herramienta solicitada ya se encuentra prestada y no ha sido devuelta");
        }

        // Validación de disponibilidad de stock de la herramienta
        if (loan.getTool().getStock() <= 0) {
            throw new RuntimeException("No quedan herramientas disponibles");
        }

        // Validación del estado de la herramienta
        if (loan.getTool().getCurrentState().getName().equals("En reparación")) {
            throw new RuntimeException("La herramienta solicitada se encuentra en reparación");
        }
        if (loan.getTool().getCurrentState().getName().equals("Dada de baja")) {
            throw new RuntimeException("La herramienta solicitada se encuentra dada de baja");
        }

        // Validación de fechas (entrega <= devolucion)
        if (loan.getReturnDate().isBefore(loan.getDeliveryDate())) {
            throw new RuntimeException("La fecha de devolución no puede ser anterior a la fecha de entrega");
        }

        // Actualiza stock y estado de la herramienta
        ToolStateEntity loaned = toolStateRepository.findByName("Prestada");
        loan.getTool().setCurrentState(loaned);
        loan.getTool().setStock(loan.getTool().getStock() - 1);
        toolRepository.save(loan.getTool());

        // Actualiza estado del préstamo
        LoanStateEntity inProgress = loanStateRepository.findByName("En progreso");
        loan.setCurrentState(inProgress);

        // Genera un nuevo movimiento del tipo 'Préstamo' en el Kardex
        KardexTypeEntity kardexType = kardexTypeRepository.findByName("Préstamo");
        KardexEntity newKardex = new KardexEntity();
        newKardex.setLoan(loan);
        newKardex.setTool(loan.getTool());
        newKardex.setClient(loan.getClient());
        newKardex.setType(kardexType);
        newKardex.setQuantity(1);
        newKardex.setMovementDate(LocalDateTime.now());

        LoanEntity savedLoan = loanRepository.save(loan);

        kardexRepository.save(newKardex);

        return savedLoan;
    }

    // RF 2.4 Calcular automáticamente multas por atraso (tarifa diaria).
    public BigDecimal overdueFine(Long loanId) {
        // Validación de la existencia en la bd del prestamo
        LoanEntity loanEntity = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime returnDate = loanEntity.getReturnDate();

        // Calcula los dias de atraso y aplica la tarifa por dia
        if (today.isAfter(returnDate)) {
            long daysLate = ChronoUnit.DAYS.between(returnDate.toLocalDate(), today.toLocalDate());
            return loanEntity.getDailyFineRate().multiply(BigDecimal.valueOf(daysLate));
        }
        return BigDecimal.ZERO;
    }

    // Actualiza automáticamente el estado de los préstamos atrasados
    @Scheduled(fixedDelay = 5000)
    public void updateOverdueLoans() {
        List<LoanEntity> loanEntities = loanRepository.findAll();
        LocalDateTime today = LocalDateTime.now();
        for (LoanEntity loanEntity : loanEntities) {
            LocalDateTime returnDate = loanEntity.getReturnDate();
            String currentLoanState =  loanEntity.getCurrentState().getName();
            if (today.isAfter(returnDate) && currentLoanState.equals("En progreso")) {
                LoanStateEntity newLoanState = loanStateRepository.findByName("Atrasado");
                loanEntity.setCurrentState(newLoanState);
                loanRepository.save(loanEntity);
            }
        }
    }

    public LoanEntity returnLoan(LoanEntity loan) {
        BigDecimal fine = overdueFine(loan.getId());
        ToolEntity tool = loan.getTool();
        ClientEntity client = loan.getClient();
        // Aplica multa si existe un atraso en la devolución del préstamo
        if (fine.compareTo(BigDecimal.ZERO) > 0) {
            loan.setTotalFine(loan.getTotalFine().add(fine));
            clientService.changeClientState(client.getId(), "Restringido");
        }
        // Aplica multa si existe un daño en la herramienta, actualiza su estado y el kardex
        if (loan.isDamaged()) {
            loan.setTotalFine(loan.getTotalFine().add(loan.getTool().getReplacementCost()));
            toolService.changeToolState(tool.getId(), "En reparación");
            clientService.changeClientState(client.getId(), "Restringido");

            // Ingresa un nuevo movimiento en el kardex
            KardexTypeEntity kardexType = kardexTypeRepository.findByName("Reparación");
            KardexEntity newKardex = new KardexEntity();
            newKardex.setTool(tool);
            newKardex.setClient(client);
            newKardex.setLoan(loan);
            newKardex.setType(kardexType);
            newKardex.setQuantity(1);
            newKardex.setMovementDate(LocalDateTime.now());
            kardexRepository.save(newKardex);
        } else {
            toolService.changeToolState(tool.getId(), "Disponible");
            tool.setStock(tool.getStock() + 1);
        }

        // Registra movimiento en el kardex
        KardexTypeEntity kardexType = kardexTypeRepository.findByName("Devolución");
        KardexEntity newKardex = new KardexEntity();
        newKardex.setTool(tool);
        newKardex.setClient(client);
        newKardex.setLoan(loan);
        newKardex.setType(kardexType);
        newKardex.setQuantity(1);
        newKardex.setMovementDate(LocalDateTime.now());

        LoanEntity savedLoan = loanRepository.save(loan);
        kardexRepository.save(newKardex);

        return savedLoan;

    }
}
