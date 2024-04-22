package com.qussai.expenseTracker.dto;

import com.qussai.expenseTracker.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class ExpenseDto {

    private Long id;
    private String title;
    private String description;
    private long amount;
    private LocalDate date;
    private Timestamp creationDate;
    private boolean completed;
    //private User user;

}
