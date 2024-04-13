package com.qussai.expenseTracker.service;



import com.qussai.expenseTracker.dto.ExpenseDto;
import com.qussai.expenseTracker.entity.Expense;
import com.qussai.expenseTracker.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseService {


    ExpenseDto addExpense(ExpenseDto expenseDto);

    ExpenseDto getExpense(Long id);

    List<ExpenseDto> getAllExpense();

    public List<Expense> findAll();

    ExpenseDto updateExpense(ExpenseDto expenseDto, Long id);

    void deleteExpense(Long id);

    ExpenseDto completeExpense(Long id);

    ExpenseDto inCompleteExpense(Long id);

     BigDecimal getTotalAmount();


}