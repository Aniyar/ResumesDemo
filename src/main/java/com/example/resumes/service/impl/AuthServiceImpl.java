package com.example.resumes.service.impl;

import com.example.resumes.model.User;
import com.example.resumes.repository.JPA.UserRepository;
import com.example.resumes.requests.AuthRequest;
import com.example.resumes.responses.AuthResponse;
import com.example.resumes.service.AuthService;
import com.example.resumes.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;

    public ResponseEntity<AuthResponse> registerUser(AuthRequest request){
        User user = new User(request);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity("User already exists", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String accessToken = jwtUtil.generateAccessToken(user);
        AuthResponse response = new AuthResponse(user.getEmail(), accessToken);

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request){
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
