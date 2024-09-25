package com.smartwash.application.auth.repositories;

import com.smartwash.application.auth.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthRepository extends JpaRepository<Auth, UUID> {
    Auth findByLogin(String login);
}
