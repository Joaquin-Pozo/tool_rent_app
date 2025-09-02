package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.KardexTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KardexTypeRepository extends JpaRepository<KardexTypeEntity,Long> {
    Optional<KardexTypeEntity> findByName(String name);
}
