package com.smartwash.application.auth.controller;

import com.smartwash.application.auth.infra.security.TokenService;
import com.smartwash.application.auth.model.Auth;
import com.smartwash.application.auth.repositories.AuthRepository;
import com.smartwash.application.auth.request.AuthenticationRequest;
import com.smartwash.application.auth.request.RegisterRequest;
import com.smartwash.application.auth.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final AuthRepository authRepository;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, AuthRepository authRepository,
                                    TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.authRepository = authRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationRequest.login(), authenticationRequest.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Auth) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }

//    @PostMapping("/register")
//    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest){
//        if(this.authRepository.findByLogin(registerRequest.login()) != null)
//            return ResponseEntity.badRequest().build();
//
//        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
//        Auth newAuthEntity = new Auth(registerRequest.login(), encryptedPassword, registerRequest.role());
//
//        this.authRepository.save(newAuthEntity);
//
//        return ResponseEntity.ok().build();
//    }
}
