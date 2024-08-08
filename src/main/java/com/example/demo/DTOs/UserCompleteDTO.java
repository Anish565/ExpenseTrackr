package com.example.demo.DTOs;

import com.example.demo.entities.User;

public record UserCompleteDTO(
    Long id,
    String username,
    String email,
    String password
){

    public User toUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toUser'");
    }

    public void setId(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }

    public void setUsername(String username2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUsername'");
    }

    public void setEmail(String email2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEmail'");
    }

    public void setPassword(String password2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPassword'");
    }

}
