package com.myproject.tool_rent_app.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tools")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    // For identification purposes, this refers to the concept of SKU
    @Column(unique = true, nullable = false)
    private String toolIdentifier;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal replacementCost;

    // Available, Loaned, Under repair, Unserviceable
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private ToolStateEntity currentState;

    private BigDecimal price;

    private int stock;

    // Tool -> Loan
    @OneToMany(mappedBy = "tool")
    private java.util.List<LoanEntity> loans;

}
