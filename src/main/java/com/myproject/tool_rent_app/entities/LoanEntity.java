package com.myproject.tool_rent_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    // Client -> Loan
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    // Tool -> Loan
    @ManyToOne
    @JoinColumn(name = "tool_id", nullable = false)
    private ToolEntity tool;

    // En progreso, Completado, Atrasado
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private LoanStateEntity currentState;

    private LocalDateTime deliveryDate;

    @Column(nullable = false)
    private LocalDateTime returnDate;

    @ColumnDefault("5000")
    private BigDecimal dailyFineRate;

    @Column(name = "damaged", nullable = false)
    @ColumnDefault("false")
    private boolean damaged;
}
