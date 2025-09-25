package com.myproject.tool_rent_app.controllers;


import com.myproject.tool_rent_app.entities.ClientEntity;
import com.myproject.tool_rent_app.entities.LoanEntity;
import com.myproject.tool_rent_app.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loans")
@CrossOrigin("*")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Muestra todos los préstamos
    @GetMapping("/")
    public ResponseEntity<List<LoanEntity>> listLoans(){
        List<LoanEntity> loans = loanService.getLoans();
        return ResponseEntity.ok(loans);
    }
    // Obtiene un préstamo por id
    @GetMapping("/{id}")
    public ResponseEntity<LoanEntity> getLoanById(@PathVariable Long id){
        LoanEntity loan = loanService.getLoanById(id);
        return ResponseEntity.ok(loan);
    }

    // Registra un nuevo prestamo
    @PostMapping("/")
    public ResponseEntity<LoanEntity> registerLoan(@RequestBody LoanEntity loan) {
        LoanEntity newLoan = loanService.registerLoan(loan);
        return ResponseEntity.ok(newLoan);
    }

    // Devuelve un prestamo
    @PutMapping("/return")
    public ResponseEntity<LoanEntity> returnLoan(@RequestBody LoanEntity loan) {
        LoanEntity returnedLoan = loanService.returnLoan(loan);
        return ResponseEntity.ok(returnedLoan);
    }

    // Calcula la multa
    @GetMapping("/fine/{id}")
    public ResponseEntity<BigDecimal> getOverdueFine(@PathVariable Long id) {
        BigDecimal fine = loanService.overdueFine(id);
        return ResponseEntity.ok(fine);
    }

    // Paga la multa
    @PutMapping("/payFine")
    public ResponseEntity<LoanEntity> payLoanFine(@RequestBody LoanEntity loan) {
        LoanEntity newLoan = loanService.payFine(loan);
        return ResponseEntity.ok(newLoan);
    }

    // Obtiene las herramientas mas solicitadas por rango de fechas
    @GetMapping("/most-loaned-tools-by-date")
    public ResponseEntity<List<Map<String, Object>>> getMostLoanedToolsByDate(
            @RequestParam(required=false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate) {
        List<Map<String, Object>> tools = loanService.getMostLoanedToolsByDate(fromDate, toDate);
        return ResponseEntity.ok(tools);
    }

    // Obtiene las herramientas mas solicitadas por rango de fechas
    @GetMapping("/most-loaned-tools")
    public ResponseEntity<List<Map<String, Object>>> getMostLoanedTools() {
        List<Map<String, Object>> tools = loanService.getMostLoanedTools();
        return ResponseEntity.ok(tools);
    }

    // Obtiene los clientes con prestamos atrasados
    @GetMapping("/clients-with-delays")
    public ResponseEntity<List<ClientEntity>> getClientsWithDelays() {
        List<ClientEntity> clients = loanService.getClientsWithDelays();
        return ResponseEntity.ok(clients);
    }

    // Obtiene los clientes con prestamos activos
    @GetMapping("/active-loans")
    public ResponseEntity<List<LoanEntity>> getActiveLoans() {
        List<LoanEntity> loans = loanService.getActiveLoans();
        return ResponseEntity.ok(loans);
    }
    // Obtiene los clientes con prestamos activos por rango de fechas
    @GetMapping("/active-loans/filter")
    public ResponseEntity<List<LoanEntity>> getActiveLoansByDate(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {
        List<LoanEntity> loans = loanService.getActiveLoansByDate(fromDate, toDate);
        return ResponseEntity.ok(loans);
    }

    // actualiza el estado de los prestamos atrasados
    @PostMapping("/update-overdue")
    public ResponseEntity<List<LoanEntity>> updateOverdueLoans() {
        List<LoanEntity> updatedLoans = loanService.updateOverdueLoans();
        return ResponseEntity.ok(updatedLoans);
    }

}
