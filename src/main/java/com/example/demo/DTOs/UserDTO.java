package com.example.demo.DTOs;

import com.example.demo.entities.User;

public record UserDTO(
    Long id,
    String username,
    String email
) {
}


