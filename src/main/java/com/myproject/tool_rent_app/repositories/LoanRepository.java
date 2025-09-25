package com.myproject.tool_rent_app.repositories;

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

    // filtra por rango de fechas solamente a los prestamos activos
    @Query(value = """
    SELECT l.*
    FROM loans l
    JOIN loan_states ls on ls.id = l.state_id
    WHERE (ls.name = 'En Progreso' OR ls.name = 'Atrasado')
    AND l.delivery_date BETWEEN :start AND :end
    """, nativeQuery = true)
    List<LoanEntity> findActiveLoansByDateBetween(LocalDate start, LocalDate end);

    // Obtiene las herramientas mas solicitadas en un rango de fechas
    @Query(value = """
    SELECT t.id as toolId, t.name as toolname, count(l.id) as totalLoans
    FROM loans l
    JOIN tools t on t.id = l.tool_id
    WHERE l.delivery_date BETWEEN :fromDate AND :toDate
    GROUP BY l.tool_id
    ORDER BY COUNT(l.id) DESC;
    """, nativeQuery = true)
    List<Object[]> findMostLoanedToolsByDateBetween(@Param("fromDate") LocalDate fromDate,
                                       @Param("toDate") LocalDate  toDate);

    // Obtiene las herramientas mas solicitadas
    @Query(value = """
    SELECT t.id as toolId, t.name as toolname, count(l.id) as totalLoans
    FROM loans l
    JOIN tools t on t.id = l.tool_id
    GROUP BY l.tool_id
    ORDER BY COUNT(l.id) DESC;
    """, nativeQuery = true)
    List<Object[]> findMostLoanedTools();

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