package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.KardexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KardexRepository extends JpaRepository<KardexEntity,Long> {

}
