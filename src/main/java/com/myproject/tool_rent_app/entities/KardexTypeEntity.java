package com.myproject.tool_rent_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kardex_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KardexTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    // ingreso, préstamo, devolución, baja, reparación
    private String name;
}
