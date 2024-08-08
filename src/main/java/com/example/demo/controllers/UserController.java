package com.example.demo.controllers;

import java.util.HashMap;
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

import com.example.demo.DTOs.ExpenseDTO;
import com.example.demo.DTOs.GroupDTO;
import com.example.demo.DTOs.SettlementDTO;
import com.example.demo.DTOs.SplitDTO;
import com.example.demo.DTOs.UserCompleteDTO;
import com.example.demo.DTOs.UserDTO;
import com.example.demo.entities.*;
import com.example.demo.repositories.ExpenseRepository;
import com.example.demo.repositories.SettlementRepository;
import com.example.demo.repositories.SplitRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.GroupServices;
import com.example.demo.services.SettlementServices;
import com.example.demo.services.SplitServices;
import com.example.demo.services.UserServices;


@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private GroupServices groupServices;

    @Autowired
    private SettlementServices settlementServices;

    @Autowired
    private SplitServices splitServices;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    // Create User
    @PostMapping(consumes = "application/json")
    public User createUser(User user) {
        return userServices.saveUser(user);
    }

    // Get User Details
    @GetMapping(path = "/{userId}", produces = "application/json")
    public Optional<UserDTO> getUserById(@PathVariable Long userId) {
        return userServices.getUserById(userId);
    }

    // Update User Details
    @PutMapping(consumes = "application/json", path = "/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserCompleteDTO userCompleteDTO) {
        User updatedUser = userServices.updateUser(userId, userCompleteDTO);
        UserDTO userDTO = new UserDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail());
        return ResponseEntity.ok(userDTO);
    }

    // Delete User
    // usable to use Void in the return type
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        return userServices.deleteUser(userId);
    }

    // get User Expenses
    @GetMapping(path = "/{userId}/expenses", produces = "application/json")
    public ResponseEntity<List<ExpenseDTO>> getUserExpenses(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<ExpenseDTO> expenses = expenseRepository.findByUser(user.get()).stream().map(
                expense -> new ExpenseDTO(expense.getId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.getUser().getId(), expense.getCategory().getName())
            ).toList();
            return ResponseEntity.ok(expenses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // get User groups
    @GetMapping(path = "/{userId}/groups", produces = "application/json")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<GroupDTO> groups = groupServices.findGroupsByUserId(userId);
            return ResponseEntity.ok(groups);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get User Settlements
    @GetMapping(path = "/{userId}/settlements", produces = "application/json")
    public ResponseEntity<List<SettlementDTO>> getUserSettlements(@PathVariable Long userId) {
        List<SettlementDTO> settlements = settlementServices.findSettlementsByUsersId(userId);
        return ResponseEntity.ok(settlements);
    }

    // Get User Splits
    @GetMapping(path = "/{userId}/splits", produces = "application/json")
    public ResponseEntity<List<SplitDTO>> getUserSplits(@PathVariable Long userId) {
        List<SplitDTO> splits = splitServices.getSplitsByUser(userId);
        return ResponseEntity.ok(splits);
    }

     // Search Users
    @GetMapping(path = "/search/query={query}", produces = "application/json")
    public ResponseEntity<List<UserDTO>> searchUsers(@PathVariable String query) {
        List<UserDTO> users = userServices.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    // Get User Balance
    @GetMapping(path = "/{userId}/balance", produces = "application/json")
    public ResponseEntity<HashMap<String, Object>> getUserBalance(@PathVariable Long userId) {
        // Implement logic to calculate user balance
        Optional<User> user = userRepository.findById(userId);
        UserDTO userDTO = new UserDTO(user.get().getId(), user.get().getUsername(), user.get().getEmail());
        HashMap<String, Object> response = new HashMap<>();

        if (user.isPresent()) {
            Double balance = calculateUserBalance(user.get());
            response.put("balance", balance);
            response.put("user", userDTO);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
        // return the jSON response with user Id and password
    }

    // Helper method to calculate user balance
    private Double calculateUserBalance(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        Double balance = expenses.stream().mapToDouble(Expense::getAmount).sum();
        return balance;
    }
}