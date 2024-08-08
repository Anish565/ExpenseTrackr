package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.example.demo.DTOs.CategoryExpenseDTO;
import com.example.demo.DTOs.ExpenseDTO;
import com.example.demo.entities.*;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ExpenseRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.ExpenseServices;
import com.example.demo.services.UserServices;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseServices expenseServices;


    @Autowired
    private UserServices userServices;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // Create Expense
    @PostMapping("/{userId}/{categoryId}")
    public ResponseEntity<ExpenseDTO> createExpense(@PathVariable Long userId, @RequestBody ExpenseDTO expense, @PathVariable Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id " + userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found with id " + categoryId));
        Expense newExpense = new Expense();
        newExpense.setAmount(expense.amount());
        newExpense.setDescription(expense.description());
        newExpense.setDate(expense.date());
        newExpense.setUser(user);
        newExpense.setCategory(category);
        expenseRepository.save(newExpense);
        ExpenseDTO expenseDTO = new ExpenseDTO(newExpense.getId(), newExpense.getAmount(), newExpense.getDescription(), newExpense.getDate(), newExpense.getUser().getId(), newExpense.getCategory().getName());
        return ResponseEntity.ok(expenseDTO);
    }

    // Get All Expenses
    @GetMapping("/{userId}/all")
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses(@PathVariable Long userId) {
        List<ExpenseDTO> expenses = expenseServices.findExpensesByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    // get specific Expense detail
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable Long expenseId) {
        Optional<ExpenseDTO> optionalExpense = expenseServices.findExpenseById(expenseId);
        if(optionalExpense.isPresent()) {
            return ResponseEntity.ok(optionalExpense.get());
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    // Update Expense
    @PutMapping("/{userId}/user/{expenseId}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long userId, @PathVariable Long expenseId, @RequestBody ExpenseDTO expenseDetails) {
        Expense updatedExpense = expenseServices.updateExpense(expenseId, expenseDetails);
        ExpenseDTO expenseDTO = new ExpenseDTO(updatedExpense.getId(), updatedExpense.getAmount(), updatedExpense.getDescription(), updatedExpense.getDate(), updatedExpense.getUser().getId(), updatedExpense.getCategory().getName());
        return ResponseEntity.ok(expenseDTO);
    }

    // Delete Expense
    @DeleteMapping("/{userId}/user/{expenseId}")
    public ResponseEntity<Object> deleteExpense(@PathVariable Long userId, @PathVariable Long expenseId) {
        return expenseServices.deleteExpense(expenseId);
    }

    // Get Expenses by Category
    @GetMapping("/{userId}/{category}")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByCategory(@PathVariable Long userId, @PathVariable Long category) {
        // check if user and category exist
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("User not found with id " + userId);
        }

        Optional<Category> categoryOpt = categoryRepository.findById(category);
        if (!categoryOpt.isPresent()) {
            throw new IllegalArgumentException("Category not found with name " + category);
        }
        List<ExpenseDTO> expenses = expenseServices.findExpensesByCategoryAndUser(categoryOpt.get(), user.get());
        return ResponseEntity.ok(expenses);

    }

    // Get total expenses per category for a user
    @GetMapping("/{userId}/category-total")
    public ResponseEntity<List<CategoryExpenseDTO>> getExpensesByCategory(@PathVariable Long userId) {
        List<CategoryExpenseDTO> expenses = expenseServices.getTotalExpensesByCategory(userId);
        return ResponseEntity.ok(expenses);
        
    }
}
