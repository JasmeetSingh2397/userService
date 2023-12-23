package com.example.userservice.controllers;

import com.example.userservice.dtos.LoginRequestDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.models.User;
import com.example.userservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequest){
        User user = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        UserDto userDto= UserDto.from(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
