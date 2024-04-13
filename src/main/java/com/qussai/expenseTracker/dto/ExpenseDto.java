package com.qussai.expenseTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {

    private Long id;
    private String title;
    private String description;
    private long amount;
    private LocalDate date;
    private Timestamp creationDate;
    private boolean completed;
}
