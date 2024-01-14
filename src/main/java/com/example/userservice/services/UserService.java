package com.example.userservice.services;

import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser= userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException("User with given id does not exist");
        }
        return optionalUser.get();
    }
}
