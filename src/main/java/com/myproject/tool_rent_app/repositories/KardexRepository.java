package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.KardexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KardexRepository extends JpaRepository<KardexEntity,Long> {
    // busca por id de herramienta
    List<KardexEntity> findByToolId(Long toolId);

    // busca por rango de fecha
    List<KardexEntity> findByMovementDateBetween(LocalDateTime start, LocalDateTime end);

    // busca por herramienta + rango de fecha
    List<KardexEntity> findByToolIdAndMovementDateBetween(Long toolId, LocalDateTime start, LocalDateTime end);
}
