package com.myproject.tool_rent_app.services;

import com.myproject.tool_rent_app.entities.ClientStateEntity;
import com.myproject.tool_rent_app.entities.LoanEntity;
import com.myproject.tool_rent_app.repositories.ClientRepository;
import com.myproject.tool_rent_app.repositories.ClientStateRepository;
import com.myproject.tool_rent_app.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientStateRepository clientStateRepository;

    @Autowired
    private ClientRepository clientRepository;

    // RF2.1 Registrar un préstamo asociando cliente y herramienta, con fecha de entrega y
    // fecha pactada de devolución. Se actualiza el kardex.
    public LoanEntity registerLoan(LoanEntity loan) {
        String clientState = "Activo";

        ClientStateEntity clientStateEntity = clientStateRepository.findByName(clientState);

        // Validación de la existencia en la bd del estado 'Activo'
        if (!clientStateEntity.getName().equals(clientState)) {
            throw new RuntimeException("El cliente no se encuentra '" + clientState + "', no puede registrar un préstamo");
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

        return loanRepository.save(loan);
    }

    // RF 2.4 Calcular automáticamente multas por atraso (tarifa diaria).
    public BigDecimal overdueFine(Long loanId) {
        LoanEntity loanEntity = loanRepository.findByLoanId(loanId);

        // Validación de la existencia en la bd del prestamo
        if (loanEntity == null) {
            throw new RuntimeException("Préstamo no encontrado");
        }

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime returnDate = loanEntity.getReturnDate();

        if (today.isAfter(returnDate)) {
            long daysLate = ChronoUnit.DAYS.between(returnDate.toLocalDate(), today.toLocalDate());
            return loanEntity.getDailyFineRate().multiply(BigDecimal.valueOf(daysLate));
        }
        return BigDecimal.ZERO;
    }
    // Multa por daño irreparable = valor de reposición de la herramienta.
    public toolDamagedFine(Long loanId) {
        LoanEntity loanEntity = loanRepository.findByLoanId(loanId);

        // Validación de la existencia en la bd del prestamo
        if (loanEntity == null) {
            throw new RuntimeException("Prestamo no encontrado");
        }

        String toolState = loanEntity.getTool().getCurrentState().getName();

        if toolState.equals("Dada de baja")

    }
}
