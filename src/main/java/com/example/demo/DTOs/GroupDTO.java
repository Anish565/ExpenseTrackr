package com.example.demo.DTOs;

import java.util.Date;
import java.util.List;

import com.example.demo.entities.User;

public record GroupDTO(
    Long id,
    String name,
    Date date,
    long admin
) {

    
}
