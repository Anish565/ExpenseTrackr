package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repositories.ExpenseRepository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.*;

public class ExpenseServices {
    
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> findAllExpenses() {
        List<Expense> expenses = (List<Expense>) expenseRepository.findAll();
        return expenses;
    }

    public Optional<Expense> findExpenseById(long id){
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense;
    }

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Expense expense) {
        expenseRepository.delete(expense);
    }
}
