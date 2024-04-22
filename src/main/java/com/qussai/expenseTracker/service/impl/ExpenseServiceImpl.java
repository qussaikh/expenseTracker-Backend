package com.qussai.expenseTracker.service.impl;

import com.qussai.expenseTracker.dto.ExpenseDto;
import com.qussai.expenseTracker.entity.Expense;
import com.qussai.expenseTracker.entity.User;
import com.qussai.expenseTracker.exception.ResourceNotFoundException;
import com.qussai.expenseTracker.repository.ExpenseRepository;
import com.qussai.expenseTracker.repository.UserRepository;
import com.qussai.expenseTracker.service.ExpenseService;
import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;

    private UserRepository userRepository;

    public Expense saveExpense(Expense expense) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Hämtar det inloggade användarnamnet

        User user = userRepository.findByUsername(username);

        expense.setUser(user);
        return expenseRepository.save(expense);
    }


    @Override
    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Hämtar det inloggade användarnamnet

        User user = userRepository.findByUsername(username);


        Expense expense = new Expense();
        expense.setUser(user);
        expense.setTitle(expenseDto.getTitle());
        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        expense.setDate(expenseDto.getDate());
        expense.setCreationDate(expenseDto.getCreationDate());
        expense.setCompleted(expenseDto.isCompleted());

        Expense savedExpense = expenseRepository.save(expense);

        ExpenseDto savedExpenseDto = new ExpenseDto();
        savedExpenseDto.setId(savedExpense.getId());
        //savedExpenseDto.setUser(savedExpense.getUser());
        savedExpenseDto.setTitle(savedExpense.getTitle());
        savedExpenseDto.setDescription(savedExpense.getDescription());
        savedExpenseDto.setAmount(savedExpense.getAmount());
        savedExpenseDto.setDate(savedExpense.getDate());
        savedExpenseDto.setCreationDate(savedExpense.getCreationDate());
        savedExpenseDto.setCompleted(savedExpense.isCompleted());

        return savedExpenseDto;
    }

    @Override
    public ExpenseDto getExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id:" + id));

        Expense savedExpense = expenseRepository.save(expense);

        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setId(savedExpense.getId());
        expenseDto.setTitle(savedExpense.getTitle());
        expenseDto.setDescription(savedExpense.getDescription());
        expenseDto.setAmount(savedExpense.getAmount());
        expenseDto.setDate(savedExpense.getDate());
        expenseDto.setCreationDate(savedExpense.getCreationDate());
        expenseDto.setCompleted(savedExpense.isCompleted());

        return expenseDto;
    }

    public List<ExpenseDto> findAllExpensesForLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        return expenseRepository.findByUser(user).stream()
                .map(expense -> {
                    ExpenseDto expenseDto = new ExpenseDto();
                    expenseDto.setId(expense.getId());
                    expenseDto.setTitle(expense.getTitle());
                    expenseDto.setDescription(expense.getDescription());
                    expenseDto.setAmount(expense.getAmount());
                    expenseDto.setDate(expense.getDate());
                    expenseDto.setCreationDate(expense.getCreationDate());
                    expenseDto.setCompleted(expense.isCompleted());
                    return expenseDto;
                }).collect(Collectors.toList());
    }


    @Override
    public ExpenseDto updateExpense(ExpenseDto expenseDto, Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id : " + id));
        expense.setTitle(expenseDto.getTitle());
        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        expense.setDate(expenseDto.getDate());
        expense.setCompleted(expenseDto.isCompleted());

        Expense updatedExpense = expenseRepository.save(expense);

        ExpenseDto updatedExpenseDto = new ExpenseDto();
        updatedExpenseDto.setId(updatedExpense.getId());
        updatedExpenseDto.setTitle(updatedExpense.getTitle());
        updatedExpenseDto.setDescription(updatedExpense.getDescription());
        updatedExpenseDto.setAmount(updatedExpense.getAmount());
        updatedExpenseDto.setDate(updatedExpense.getDate());
        updatedExpenseDto.setCompleted(updatedExpense.isCompleted());

        return updatedExpenseDto;
    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id : " + id));

        expenseRepository.deleteById(id);
    }

    @Override
    public ExpenseDto completeExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id : " + id));

        expense.setCompleted(Boolean.TRUE);

        Expense updatedExpense = expenseRepository.save(expense);

        ExpenseDto updatedExpenseDto = new ExpenseDto();
        updatedExpenseDto.setId(updatedExpense.getId());
        updatedExpenseDto.setTitle(updatedExpense.getTitle());
        updatedExpenseDto.setDescription(updatedExpense.getDescription());
        updatedExpenseDto.setAmount(updatedExpense.getAmount());
        updatedExpenseDto.setDate(updatedExpense.getDate());
        updatedExpenseDto.setCompleted(updatedExpense.isCompleted());

        return updatedExpenseDto;
    }

    @Override
    public ExpenseDto inCompleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id : " + id));

        expense.setCompleted(Boolean.FALSE);

        Expense updatedExpense = expenseRepository.save(expense);

        ExpenseDto updatedExpenseDto = new ExpenseDto();
        updatedExpenseDto.setId(updatedExpense.getId());
        updatedExpenseDto.setTitle(updatedExpense.getTitle());
        updatedExpenseDto.setDescription(updatedExpense.getDescription());
        updatedExpenseDto.setAmount(updatedExpense.getAmount());
        updatedExpenseDto.setDate(updatedExpense.getDate());
        updatedExpenseDto.setCompleted(updatedExpense.isCompleted());

        return updatedExpenseDto;
    }

    @Override
    public BigDecimal getTotalAmount() {
        List<Expense> expenses = expenseRepository.findAll();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Expense expense : expenses) {
            totalAmount = totalAmount.add(BigDecimal.valueOf(expense.getAmount()));
        }
        return totalAmount;
    }



}
