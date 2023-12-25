package com.example.userservice.repositories;

import com.example.userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session save(Session session);
    Optional<Session> findByTokenAndUserId(String token, long userId);


}
