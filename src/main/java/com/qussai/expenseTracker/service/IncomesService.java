package com.qussai.expenseTracker.service;

import com.qussai.expenseTracker.dto.IncomeDto;

import java.util.List;

public interface IncomesService {

    IncomeDto saveIncomes(IncomeDto incomes);

    List<IncomeDto> getUserIncome();
}
