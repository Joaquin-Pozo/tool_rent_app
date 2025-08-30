package com.myproject.tool_rent_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    // For identification purposes, this refers to the concept of RUT
    @Column(unique = true, nullable = false)
    private String documentNumber;

    // Active, Restricted
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private ClientStateEntity currentState;

    // Client -> Loan
    @OneToMany(mappedBy = "client")
    private java.util.List<LoanEntity> loans;
}
