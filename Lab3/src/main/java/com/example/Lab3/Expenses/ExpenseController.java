package com.example.Lab3.Expenses;

import com.example.Lab3.Expenses.dto.CreateExpenseDTO;
import com.example.Lab3.Expenses.dto.UpdateExpenseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blanco/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Optional<Expense> expenseOptional = expenseService.getExpenseById(id);

        if (expenseOptional.isPresent()) {
            Expense expense = expenseOptional.get();
            return ResponseEntity.ok(expense);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody CreateExpenseDTO expenseDTO) {
        Expense newExpense = expenseService.createExpense(expenseDTO);
        return new ResponseEntity<>(newExpense, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Valid @RequestBody UpdateExpenseDTO updateExpenseDTO) {
        try {
            Expense updatedExpense = expenseService.updateExpense(id, updateExpenseDTO);
            return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        try {
            expenseService.deleteExpense(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}