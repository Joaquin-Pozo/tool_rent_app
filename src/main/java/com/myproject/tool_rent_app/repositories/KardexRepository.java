package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.KardexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KardexRepository extends JpaRepository<KardexEntity,Long> {
    List<KardexEntity> findByToolId(Long toolId);

    List<KardexEntity> findByMovementDateBetween(LocalDateTime start, LocalDateTime end);
}
