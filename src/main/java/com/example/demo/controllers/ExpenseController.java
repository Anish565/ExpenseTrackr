package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.example.demo.entities.*;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ExpenseRepository;
import com.example.demo.repositories.UserRepository;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // Create Expense
    @PostMapping("/{userId}")
    public Expense createExpense(@PathVariable Long userId, @RequestBody Expense expense) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(user -> {
                    expense.setUser(user);
                    return expenseRepository.save(expense);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + userId));
    }

    // Get All Expenses
    @GetMapping("/{userId}")
    public List<Expense> getAllExpenses(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> user.getExpenses())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + userId));
    }

    // get specific Expense detail
    @GetMapping("/{userId}/user/{expenseId}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long userId, @PathVariable Long expenseId) {
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent() && expenseOptional.isPresent()) {
            // get the expense of the user and check if it exists
            User user = userOptional.get();
            Expense expense = expenseOptional.get();
            
            if (user.getExpenses().contains(expense)) {
                return ResponseEntity.ok(expense);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // Update Expense
    @PutMapping("/{userId}/user/{expenseId}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long userId, @PathVariable Long expenseId, @RequestBody Expense expenseDetails) {
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        Optional<User> user = userRepository.findById(userId);
        System.out.println(optionalExpense);
        if(optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setAmount(expenseDetails.getAmount());
            expense.setCategory(expenseDetails.getCategory());
            expense.setDescription(expenseDetails.getDescription());
            expense.setDate(expenseDetails.getDate());
            final Expense updatedExpense = expenseRepository.save(expense);
            return ResponseEntity.ok(updatedExpense);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Expense
    @DeleteMapping("/{userId}/user/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long userId, @PathVariable Long expenseId) {
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        if(optionalExpense.isPresent()) {
            expenseRepository.delete(optionalExpense.get());
            return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    // Get Expense by Category
    @GetMapping("/{userId}/{category}")
    public List<Expense> getExpenseByCategory(@PathVariable Long userId, @PathVariable Long category) {
        // check if user and category exist
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("User not found with id " + userId);
        }

        Optional<Category> categoryOpt = categoryRepository.findById(category);
        if (!categoryOpt.isPresent()) {
            throw new IllegalArgumentException("Category not found with name " + category);
        }
        List<Expense> expenses = expenseRepository.findByUserAndCategory(user, categoryOpt);
        return expenses;

    }
}
