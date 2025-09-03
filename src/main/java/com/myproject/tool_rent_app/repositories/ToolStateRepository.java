package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.ToolStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolStateRepository extends JpaRepository<ToolStateEntity,Long> {
    ToolStateEntity findByName(String name);
}
