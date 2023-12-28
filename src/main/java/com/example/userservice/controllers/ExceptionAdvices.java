//package com.example.userservice.controllers;
//
//import com.example.userservice.exceptions.IncorrectPasswordException;
//import com.example.userservice.exceptions.InvalidSessionException;
//import com.example.userservice.exceptions.UserAlreadyExistsException;
//import com.example.userservice.exceptions.UserNotFoundException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class ExceptionAdvices {
//
//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<String> handleException(Exception e){
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
//
//    @ExceptionHandler({UserNotFoundException.class})
//    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e){
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//    }
//
//    @ExceptionHandler({IncorrectPasswordException.class})
//    public ResponseEntity<String> handleIncorrectPasswordException(IncorrectPasswordException e){
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//    }
//    @ExceptionHandler({JsonProcessingException.class})
//    public ResponseEntity<String> handleJsonProcessingException(JsonProcessingException e){
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
//
//    @ExceptionHandler({InvalidSessionException.class})
//    public ResponseEntity<String> handleInvalidSessionException(InvalidSessionException e){
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//    }
//
//    @ExceptionHandler({UserAlreadyExistsException.class})
//    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e){
//        return new ResponseEntity<>(HttpStatus.CONFLICT);
//
//    }
//
//
//
//}
