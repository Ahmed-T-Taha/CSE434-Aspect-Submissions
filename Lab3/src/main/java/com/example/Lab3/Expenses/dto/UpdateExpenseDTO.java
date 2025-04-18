package com.example.Lab3.Expenses.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExpenseDTO {
    private String name;

    @PositiveOrZero(message = "Amount must be positive")
    private Double amount;

    private String description;

    private Date date;
}


