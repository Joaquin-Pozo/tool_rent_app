package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity,Long> {

    List<LoanEntity> findByClientId(Long clientId);
}
