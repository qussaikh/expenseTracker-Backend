package com.qussai.expenseTracker.repository;

import com.qussai.expenseTracker.entity.User;
import com.qussai.expenseTracker.entity.Incomes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncomesRepository extends JpaRepository <Incomes, Long> {

    Optional<Incomes> findByUser(User user);
}
