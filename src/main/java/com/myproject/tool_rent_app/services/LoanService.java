package com.myproject.tool_rent_app.services;

import com.myproject.tool_rent_app.entities.ClientStateEntity;
import com.myproject.tool_rent_app.entities.KardexEntity;
import com.myproject.tool_rent_app.entities.KardexTypeEntity;
import com.myproject.tool_rent_app.entities.LoanEntity;
import com.myproject.tool_rent_app.repositories.ClientRepository;
import com.myproject.tool_rent_app.repositories.ClientStateRepository;
import com.myproject.tool_rent_app.repositories.KardexTypeRepository;
import com.myproject.tool_rent_app.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private KardexTypeRepository kardexTypeRepository;

    @Autowired
    private ClientStateRepository clientStateRepository;

    @Autowired
    private ClientRepository clientRepository;

    // RF2.1 Registrar un préstamo asociando cliente y herramienta, con fecha de entrega y
    // fecha pactada de devolución. Se actualiza el kardex.
    public LoanEntity registerLoan(LoanEntity loan) {
        String activeState = "Activo";

        // Validación del estado 'Activo' del cliente
        String clientState = loan.getClient().getCurrentState().getName();
        if (!clientState.equals(activeState)) {
            throw new RuntimeException("El cliente no se encuentra '" +  activeState + "', no puede registrar un préstamo");
        }

        // Verifica si el cliente esta al dia (no tiene prestamos atrasados o multas pendientes)
        List<LoanEntity> previousLoans = loanRepository.findByClientId(loan.getClient().getId());

        for (LoanEntity prevLoan : previousLoans) {
            if (overdueFine(prevLoan.getId()).compareTo(BigDecimal.ZERO) > 0) {
                throw new RuntimeException("El cliente tiene multas impagas por prestamos atrasados");
            }

            if (prevLoan.isDamaged()) {
                throw new RuntimeException("El cliente tiene una multa por reposición de herramienta dañada");
            }
        }

        // Validación de disponibilidad de stock de la herramienta
        if (loan.getTool().getStock() <= 0) {
            throw new RuntimeException("No quedan herramientas disponibles");
        }

        // Validación del estado de la herramienta
        if (!loan.getTool().getCurrentState().getName().equals("Disponible")) {
            throw new RuntimeException("La herramienta no se encuentra disponible");
        }

        // Validación de fechas (entrega <= devolucion)
        if (loan.getReturnDate().isBefore(loan.getReturnDate())) {
            throw new RuntimeException("La fecha de devolución no puede ser anterior a la fecha de entrega");
        }

        // Genera un nuevo movimiento del tipo 'Préstamo' en el Kardex
        KardexTypeEntity kardexType = kardexTypeRepository.findByName("Préstamo");
        KardexEntity newKardex = new KardexEntity();
        newKardex.setLoan(loan);
        newKardex.setTool(loan.getTool());
        newKardex.setClient(loan.getClient());
        newKardex.setType(kardexType);
        newKardex.setQuantity(1);
        newKardex.setMovementDate(LocalDateTime.now());

        return loanRepository.save(loan);
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
    // Multa por daño irreparable = valor de reposición de la herramienta.
    /*
    public toolDamagedFine(Long loanId) {
        LoanEntity loanEntity = loanRepository.findByLoanId(loanId);

        // Validación de la existencia en la bd del prestamo
        if (loanEntity == null) {
            throw new RuntimeException("Prestamo no encontrado");
        }

        String toolState = loanEntity.getTool().getCurrentState().getName();

        if toolState.equals("Dada de baja")

    }
    */
}
