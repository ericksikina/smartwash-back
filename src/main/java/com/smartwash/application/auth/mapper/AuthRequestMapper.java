package com.smartwash.application.auth.mapper;

import com.smartwash.application.auth.enums.UserRole;
import com.smartwash.application.auth.model.Auth;
import com.smartwash.application.auth.request.RegisterRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthRequestMapper {
    public static Auth toAuth(final RegisterRequest registerRequest, Auth auth) {
        auth.setLogin(registerRequest.login());
        auth.setPassword(new BCryptPasswordEncoder().encode(registerRequest.password()));
        auth.setRole(UserRole.ADMIN);

        return auth;
    }
}