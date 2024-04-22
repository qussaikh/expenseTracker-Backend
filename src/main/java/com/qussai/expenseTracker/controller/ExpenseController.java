package com.qussai.expenseTracker.controller;

import com.qussai.expenseTracker.dto.ExpenseDto;
import com.qussai.expenseTracker.entity.Expense;
import com.qussai.expenseTracker.entity.User;
import com.qussai.expenseTracker.service.ExpenseService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.math.BigDecimal;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/invoice")
@AllArgsConstructor
public class ExpenseController {

    private ExpenseService expenseService;

    // Build Add Todo REST API

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto){
        ExpenseDto savedExpense = expenseService.addExpense(expenseDto);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    // Build Get Todo REST API
    @GetMapping("{id}")
    public ResponseEntity<ExpenseDto> getExpense(@PathVariable("id") Long expenseId){
        ExpenseDto expenseDto = expenseService.getExpense(expenseId);
        return new ResponseEntity<>(expenseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpensesForLoggedInUser() {
        List<ExpenseDto> expenses = expenseService.findAllExpensesForLoggedInUser();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Build Update Todo REST API
    @PutMapping("{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@RequestBody ExpenseDto expenseDto, @PathVariable("id") Long expenseId){
        ExpenseDto updatedExpense = expenseService.updateExpense(expenseDto, expenseId);
        return ResponseEntity.ok(updatedExpense);
    }

    // Build Delete Todo REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long expenseId){
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok("Todo deleted successfully!.");
    }

    // Build Complete Todo REST API
    @PatchMapping("{id}/complete")
    public ResponseEntity<ExpenseDto> completeExpense(@PathVariable("id") Long expenseId){
        ExpenseDto updatedExpense = expenseService.completeExpense(expenseId);
        return ResponseEntity.ok(updatedExpense);
    }

    // Build In Complete Todo REST API
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<ExpenseDto> inCompleteExpense(@PathVariable("id") Long expenseId){
        ExpenseDto updatedExpense = expenseService.inCompleteExpense(expenseId);
        return ResponseEntity.ok(updatedExpense);
    }

    @GetMapping("/totalAmount")
    public ResponseEntity<BigDecimal> getTotalAmount (){
        BigDecimal totalAmount = expenseService.getTotalAmount();
        return ResponseEntity.ok(totalAmount);
    }

}
