package com.example.demo.DTOs;

public record SplitDTO(
    long id,
    Double amount,
    long groupId,
    long payerId,
    long payeeId,
    String categoryName,
    Boolean settled

) {

    
}
