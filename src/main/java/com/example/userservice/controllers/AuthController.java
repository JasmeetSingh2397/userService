package com.example.userservice.controllers;

import com.example.userservice.dtos.*;
import com.example.userservice.exceptions.*;
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

import java.util.Optional;

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
    public ResponseEntity<ValidateTokenResponseDto> validate(@RequestBody ValidateTokenRequestDto validateTokenRequestDto ){
        Optional<UserDto> optionalUserDto = authService.validate(validateTokenRequestDto.getToken(), validateTokenRequestDto.getUserId());
        ValidateTokenResponseDto response= new ValidateTokenResponseDto();
        if (optionalUserDto.isEmpty()){
            response.setSessionStatus(SessionStatus.INVALID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setSessionStatus(SessionStatus.ACTIVE);
        response.setUserDto(optionalUserDto.get());
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws InvalidSessionException {
        authService.logout(logoutRequestDto.getToken(), logoutRequestDto.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) throws UserAlreadyExistsException {
        UserDto userDto= authService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword());
        return new ResponseEntity<>(userDto,HttpStatus.OK);

    }
}
