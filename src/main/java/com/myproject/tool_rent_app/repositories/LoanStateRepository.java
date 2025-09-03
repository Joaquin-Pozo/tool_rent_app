package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.LoanStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanStateRepository extends JpaRepository<LoanStateEntity,Long> {
}
