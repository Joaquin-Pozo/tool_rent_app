package com.myproject.tool_rent_app.repositories;

import com.myproject.tool_rent_app.entities.ClientEntity;
import com.myproject.tool_rent_app.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity,Long> {

    List<LoanEntity> findByClientId(Long clientId);

    List<LoanEntity> findByToolIdAndCurrentStateName(Long toolId, String currentStateName);

    // Obtiene las herramientas mas solicitadas
    @Query(value = """
    SELECT l.tool_id as toolId, t.name as toolName, count(l.id) as totalLoans
    FROM loans l
    INNER JOIN tools t on t.id = l.tool_id
    WHERE (:fromDate IS NULL OR l.delivery_date >= :fromDate)
    AND (:toDate IS NULL OR l.delivery_date <= :toDate)
    GROUP BY l.tool_id, t.name
    ORDER BY COUNT(l.id) DESC;
    """, nativeQuery = true)
    List<Object[]> findMostLoanedTools(@Param("fromDate") LocalDate fromDate,
                                       @Param("toDate") LocalDate  toDate);

    // obtiene los prestamos con atrasos
    @Query(value = """
    SELECT l.*
    FROM loans l
    JOIN loan_states ls on ls.id = l.state_id
    WHERE ls.name = 'Atrasado'
    """, nativeQuery = true)
    List<LoanEntity> findLoanswithDelays();

    // obtiene los prestamos 'En progreso' o 'Atrasado'
    @Query(value = """
    SELECT l.*
    FROM loans l
    JOIN loan_states ls on ls.id = l.state_id
    WHERE ls.name = 'En progreso' OR ls.name = 'Atrasado'
    """, nativeQuery = true)
    List<LoanEntity> findActiveLoans();
}