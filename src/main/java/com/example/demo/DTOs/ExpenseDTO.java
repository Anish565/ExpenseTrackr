package com.example.demo.DTOs;

import java.util.Date;

import com.example.demo.entities.Category;
import com.example.demo.entities.User;

public record ExpenseDTO(
    long id,
    Double amount,
    String description,
    Date date,
    long userId,
    String categoryName
) {

	
    
}
