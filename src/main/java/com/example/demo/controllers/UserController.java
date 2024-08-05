package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.*;

import com.example.demo.entities.*;
import com.example.demo.repositories.ExpenseRepository;
import com.example.demo.repositories.SettlementRepository;
import com.example.demo.repositories.SplitRepository;
import com.example.demo.repositories.UserRepository;


@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    // Create User
    @PostMapping(consumes = "application/json")
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Get User Details
    @GetMapping(path = "/{userId}", consumes = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userRepository.findById(userId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update User Details
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        
        return userRepository.findById(userId)
                    .map(user -> {
                        user.setUsername(userDetails.getUsername());
                        user.setEmail(userDetails.getEmail());
                        user.setPassword(userDetails.getPassword());
                        return ResponseEntity.ok(userRepository.save(user));
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete User
    // usable to use Void in the return type
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // get User Expenses
    @GetMapping("/{userId}/expenses")
    public ResponseEntity<List<Expense>> getUserExpenses(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<Expense> expenses = expenseRepository.findByUser(user.get());
            return ResponseEntity.ok(expenses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // get User groups
    @GetMapping("/{userId}/groups")
    public ResponseEntity<List<Group>> getUserGroups(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<Group> groups = user.get().getGroups();
            return ResponseEntity.ok(groups);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get User Settlements
    @GetMapping("/{userId}/settlements")
    public ResponseEntity<List<Settlements>> getUserSettlements(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(user.getPaidSettlements()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get User Splits
    @GetMapping("/{groupId}/{userId}/splits")
    public ResponseEntity<List<Split>> getUserSplits(@PathVariable Long groupId, @PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(user.getPaySplits()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

     // Search Users
    @GetMapping("/search/query={query}")
    public ResponseEntity<List<User>> searchUsers(@PathVariable String query) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(query);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    // Get User Balance
    @GetMapping("/{userId}/balance")
    public ResponseEntity<Integer> getUserBalance(@PathVariable Long userId) {
        // Implement logic to calculate user balance
        int balance = calculateUserBalance(userId);
        return ResponseEntity.ok(balance);
    }

    // Helper method to calculate user balance
    private int calculateUserBalance(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<Expense> expenses = expenseRepository.findByUser(user.get());
            int balance = expenses.stream().mapToInt(Expense::getAmount).sum();
            return balance;
        } else {
            return 0;
        }
    }
}