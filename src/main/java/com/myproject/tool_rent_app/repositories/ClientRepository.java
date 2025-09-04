package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity,Long> {
}
