package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends BaseModel{

    private String email;
    private String password;
    private String fullName;

    @ManyToMany
    private Set<Role> roles;

}
