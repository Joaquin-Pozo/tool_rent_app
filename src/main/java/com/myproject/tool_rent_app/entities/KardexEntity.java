package com.myproject.tool_rent_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "kardex")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KardexEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tool_id")
    private ToolEntity tool;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanEntity loan;

    @ManyToOne
    @JoinColumn(name = "type_id",  nullable = false)
    private KardexTypeEntity type;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDateTime movementDate;

}
