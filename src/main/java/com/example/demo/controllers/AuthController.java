package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.UserCompleteDTO;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserServices;

import jakarta.persistence.EntityManager;


@RestController
@RequestMapping("/auth")
public class AuthController {



    @Autowired
    private UserServices userServices;


    // Registering a new user
    @PostMapping(path = "/register", consumes = "application/json")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User createdUser = userServices.saveUser(user);
        
        return ResponseEntity.ok("User registration successful" + createdUser);
        
    }

    // Login User
    @GetMapping(path = "/login", produces = "application/json")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        Optional<UserCompleteDTO> foundUser = userServices.getUserCompleteByUsername(user.getUsername());
        
        if (foundUser.isPresent() && foundUser.get().password().equals(user.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // Logout User
    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        // Implement logic to logout user
        return ResponseEntity.ok("Logout successful");
    }

    // @PostMapping("/post")
    // public void postUser() {
    //     EntityManager entityManager = null;
    // }
    
}