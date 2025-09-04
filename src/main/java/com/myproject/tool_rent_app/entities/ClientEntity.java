package com.myproject.tool_rent_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(unique = true, nullable = false)
    private String documentNumber;

    // Activo, Restringido
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private ClientStateEntity currentState;

    // Client -> Loan
    @OneToMany(mappedBy = "client")
    private java.util.List<LoanEntity> loans;
}
