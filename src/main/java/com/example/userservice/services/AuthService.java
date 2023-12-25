package com.example.userservice.services;

import com.example.userservice.dtos.LoginResponseDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.IncorrectPasswordException;
import com.example.userservice.exceptions.InvalidSessionException;
import com.example.userservice.exceptions.UserAlreadyExistsException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.Role;
import com.example.userservice.models.Session;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.models.User;
import com.example.userservice.repositories.SessionRepository;
import com.example.userservice.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    private SessionRepository sessionRepository;




    public LoginResponseDto login(String email, String password) throws UserNotFoundException, IncorrectPasswordException, JsonProcessingException {
        Optional<User> optionalUser= userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException("User with given email does not exist");

        }
        User user= optionalUser.get();
        if(!bcryptPasswordEncoder.matches(password, user.getPassword())){
            throw new IncorrectPasswordException("Incorrect Password");
        }

        Key key= Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Set<Role> roles= user.getRoles();
        ObjectMapper mapper = new ObjectMapper();
        String rolesJson = mapper.writeValueAsString(roles);

        JwtBuilder builder= Jwts.builder().
                setHeaderParam("typ", "JWT").
                setHeaderParam("alg", "HS256").
                setSubject(""+user.getId()).
                claim("roles", rolesJson).
                setIssuedAt(new Date()).
                setExpiration(new Date(System.currentTimeMillis()+60000));

        String token= builder.signWith(key).compact();

        Session session= new Session();
        session.setToken(token);
        session.setUser(user);
        session.setCreatedAt(new Date());
        session.setLastModifiedAt(new Date());
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        LoginResponseDto loginResponseDto= new LoginResponseDto();
        loginResponseDto.setUserDto(UserDto.from(user));
        loginResponseDto.setToken(token);

        return loginResponseDto;



    }
    public SessionStatus validate(String token, long userId){
        Optional<Session> optionalSession= sessionRepository.findByTokenAndUserId(token, userId);
        if (optionalSession.isEmpty()){
            return SessionStatus.INVALID;
        }
        Session session= optionalSession.get();
        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return SessionStatus.EXPIRED;
        }
        return SessionStatus.ACTIVE;



    }

    public void logout(String token, long userId) throws InvalidSessionException {

        Optional<Session> optionalSession= sessionRepository.findByTokenAndUserId(token, userId);
        if (optionalSession.isEmpty()){
            throw new InvalidSessionException("Invalid Session");
        }

        Session session= optionalSession.get();
        session.setSessionStatus(SessionStatus.LOGGED_OUT);
        sessionRepository.save(session);
    }

    public UserDto signup(String email, String password) throws UserAlreadyExistsException {
        Optional<User> optionalUser= userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException("User with email "+email+" already exists");
        }
        User user= new User();
        user.setEmail(email);
        String encPassword= bcryptPasswordEncoder.encode(password);
        user.setPassword(encPassword);
        User savedUser= userRepository.save(user);
        return UserDto.from(savedUser);

    }

}
