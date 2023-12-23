package com.example.userservice.services;

import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository authRepository;

    public AuthService(UserRepository authRepository) {
        this.authRepository = authRepository;
    }

    public User login(String email, String password){
        return null;

    }


}
