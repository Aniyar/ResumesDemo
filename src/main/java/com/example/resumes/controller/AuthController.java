package com.example.resumes.controller;

import com.example.resumes.model.User;
import com.example.resumes.requests.AuthRequest;
import com.example.resumes.responses.AuthResponse;
import com.example.resumes.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;



@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/register/user")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody @Valid AuthRequest request) {
        request.setRole(User.Role.USER);
        return authService.registerUser(request);
    }

    @PostMapping("/register/HR")
    public ResponseEntity<AuthResponse> registerHR(@RequestBody @Valid AuthRequest request) {
        request.setRole(User.Role.HR);
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        return authService.login(request);
    }

}

