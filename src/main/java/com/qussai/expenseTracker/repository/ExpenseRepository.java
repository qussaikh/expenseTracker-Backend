package com.qussai.expenseTracker.repository;


import com.qussai.expenseTracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
