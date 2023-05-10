package com.example.resumes.service;

import com.example.resumes.requests.AuthRequest;
import com.example.resumes.responses.AuthResponse;
import org.springframework.http.ResponseEntity;


public interface AuthService {
    ResponseEntity<AuthResponse> registerUser(AuthRequest request);

    ResponseEntity<AuthResponse> login(AuthRequest request);
}
