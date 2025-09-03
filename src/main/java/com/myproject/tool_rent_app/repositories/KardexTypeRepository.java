package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.KardexTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KardexTypeRepository extends JpaRepository<KardexTypeEntity,Long> {
    KardexTypeEntity findByName(String name);
}
