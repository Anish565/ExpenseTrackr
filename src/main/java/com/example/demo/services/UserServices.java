package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;

public class UserServices {
    
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllusers() {
        List<User> users = (List<User>)userRepository.findAll();
        return users;
    }

    public Optional<User> findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }



}
