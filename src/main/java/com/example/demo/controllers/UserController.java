package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.*;

import com.example.demo.entities.*;
import com.example.demo.repositories.UserRepository;

public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    // Create User
    @PostMapping
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Get User Details
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userRepository.findById(userId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update User Details
    @PutMapping("/{userId}")
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
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(user.getExpenses()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // get User groups
    @GetMapping("/{userId}/groups")
    public ResponseEntity<List<Group>> getUserGroups(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(user.getGroups()))
                .orElseGet(() -> ResponseEntity.notFound().build());
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
}
