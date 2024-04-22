package com.qussai.expenseTracker.controller;


import com.qussai.expenseTracker.dto.IncomeDto;
import com.qussai.expenseTracker.entity.Incomes;
import com.qussai.expenseTracker.service.IncomesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/incomes")
@AllArgsConstructor
public class IncomesController {

    private IncomesService incomesService;


    @PostMapping
    public ResponseEntity<IncomeDto> addExpense(@RequestBody IncomeDto incomeDto){
        IncomeDto savedIncome = incomesService.saveIncomes(incomeDto);
        return new ResponseEntity<>(savedIncome, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IncomeDto>> getUserIncome (){
        List<IncomeDto> incomes = incomesService.getUserIncome();
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }





}
