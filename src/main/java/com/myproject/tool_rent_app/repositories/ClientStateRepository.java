package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.ClientStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientStateRepository extends JpaRepository<ClientStateEntity, Long> {
    ClientStateEntity findByName(String name);
}
