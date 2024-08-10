package com.example.demo.DTOs;

import com.example.demo.entities.User;

public record UserCompleteDTO(
    Long id,
    String username,
    String email,
    String password
){

    

}
