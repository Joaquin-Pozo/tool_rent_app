package com.myproject.tool_rent_app.controllers;


import com.myproject.tool_rent_app.entities.LoanEntity;
import com.myproject.tool_rent_app.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Repository
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Registra un prestamo
    @PostMapping
    public LoanEntity registerLoan(@RequestBody LoanEntity loan) {
        return loanService.registerLoan(loan);
    }

    // Devuelve un prestamo
    @PutMapping("/{id}/return")
    public LoanEntity returnLoan(@PathVariable Long id, @RequestBody LoanEntity loan) {
        loan.setId(id);
        return loanService.returnLoan(loan);
    }

    // Calcula la multa
    @GetMapping("/{id}/fine")
    public BigDecimal getOverdueFine(@PathVariable Long id) {
        return loanService.overdueFine(id);
    }
}
