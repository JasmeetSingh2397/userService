package com.example.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Session extends BaseModel{

    private String token;
    private User user;

}
