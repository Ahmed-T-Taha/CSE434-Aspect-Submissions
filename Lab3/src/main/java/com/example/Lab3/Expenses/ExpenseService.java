package com.example.Lab3.Expenses;

import com.example.Lab3.Expenses.dto.CreateExpenseDTO;
import com.example.Lab3.Expenses.dto.UpdateExpenseDTO;
import com.example.Lab3.Users.User;
import com.example.Lab3.Users.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.*;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;


    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    public Expense createExpense(CreateExpenseDTO expenseDTO) {
        User user = userService.getUserById(expenseDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseDTO.getUserId()));
        Expense expense = new Expense(
                expenseDTO.getName(),
                expenseDTO.getAmount(),
                expenseDTO.getDescription(),
                expenseDTO.getDate(),
                user
        );
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, UpdateExpenseDTO expenseDTO) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        BeanUtils.copyProperties(expenseDTO, existingExpense, getNullPropertyNames(expenseDTO));
        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        expenseRepository.delete(expense);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
