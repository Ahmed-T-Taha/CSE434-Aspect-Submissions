package com.example.Lab3.Expenses;

import com.example.Lab3.Users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double amount;
    private String description;
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Expense(String name, Double amount, String description, Date date, User user) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.user = user;
    }
}
