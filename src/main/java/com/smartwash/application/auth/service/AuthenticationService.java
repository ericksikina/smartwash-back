package com.smartwash.application.auth.service;

import com.smartwash.application.auth.repositories.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    private final AuthRepository authRepository;

    public AuthenticationService(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.authRepository.findByLogin(username);
    }
}
