package com.qussai.expenseTracker.service.impl;

import com.qussai.expenseTracker.dto.IncomeDto;
import com.qussai.expenseTracker.entity.Incomes;
import com.qussai.expenseTracker.entity.User;
import com.qussai.expenseTracker.repository.ExpenseRepository;
import com.qussai.expenseTracker.repository.IncomesRepository;
import com.qussai.expenseTracker.repository.UserRepository;
import com.qussai.expenseTracker.service.IncomesService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IncomesServiceImpl implements IncomesService {

    private IncomesRepository incomesRepository;

    private UserRepository userRepository;
    @Override
    public IncomeDto saveIncomes(IncomeDto incomesDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        // Attempt to find an existing income for the user
        Optional<Incomes> existingIncome = incomesRepository.findByUser(user);

        Incomes income;
        if (existingIncome.isPresent()) {
            // If income exists, update it
            income = existingIncome.get();
            income.setIncome(incomesDto.getIncome());
        } else {
            // If no income exists, create a new one
            income = new Incomes();
            income.setUser(user);
            income.setIncome(incomesDto.getIncome());
        }

        // Save the income (new or updated)
        Incomes savedIncome = incomesRepository.save(income);

        // Return the DTO
        IncomeDto savedIncomeDto = new IncomeDto();
        savedIncomeDto.setId(savedIncome.getId());
        savedIncomeDto.setIncome(savedIncome.getIncome());

        return savedIncomeDto;
    }


    @Override
    public List<IncomeDto> getUserIncome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        return  incomesRepository.findByUser(user).stream()
                .map(incomes -> {
                    IncomeDto incomeDto = new IncomeDto();
                    incomeDto.setId(incomes.getId());
                    incomeDto.setIncome(incomes.getIncome());
                    return incomeDto;
                } ).collect(Collectors.toList());
    }


}
