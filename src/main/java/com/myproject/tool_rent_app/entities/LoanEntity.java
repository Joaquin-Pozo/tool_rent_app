package com.myproject.tool_rent_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.Date;

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

    private Date deliveryDate;

    @Column(nullable = false)
    private Date returnDate;

    @ColumnDefault("5000")
    private BigDecimal dailyFineRate;

    // In progress, Completed, Overdue (the client has not paid yet)
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private LoanStateEntity currentState;
}
