package com.example.userservice.controllers;

import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long userId) throws UserNotFoundException {



        User user = userService.getUser(userId);
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);

    }

}
