package com.qussai.expenseTracker.repository;


import com.qussai.expenseTracker.entity.Expense;
import com.qussai.expenseTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);

}
