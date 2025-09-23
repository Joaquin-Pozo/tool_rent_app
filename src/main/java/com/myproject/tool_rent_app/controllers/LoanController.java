package com.myproject.tool_rent_app.controllers;


import com.myproject.tool_rent_app.entities.LoanEntity;
import com.myproject.tool_rent_app.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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
}
