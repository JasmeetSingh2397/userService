package com.example.userservice.controllers;

import com.example.userservice.dtos.*;
import com.example.userservice.exceptions.IncorrectPasswordException;
import com.example.userservice.exceptions.InvalidSessionException;
import com.example.userservice.exceptions.UserAlreadyExistsException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.models.User;
import com.example.userservice.services.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequest) throws UserNotFoundException, IncorrectPasswordException, JsonProcessingException {
        LoginResponseDto loginResponseDto = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        MultiValueMap<String,String> headers= new LinkedMultiValueMap<>();
        headers.add("AUTH-TOKEN", loginResponseDto.getToken());

        return new ResponseEntity<>(loginResponseDto.getUserDto(), headers, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateTokenRequestDto validateTokenRequestDto ){
        SessionStatus sessionStatus= authService.validate(validateTokenRequestDto.getToken(), validateTokenRequestDto.getUserId());
        return new ResponseEntity<>(sessionStatus,HttpStatus.OK);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws InvalidSessionException {
        authService.logout(logoutRequestDto.getToken(), logoutRequestDto.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(SignupRequestDto signupRequestDto) throws UserAlreadyExistsException {
        UserDto userDto= authService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword());
        return new ResponseEntity<>(userDto,HttpStatus.OK);

    }
}
