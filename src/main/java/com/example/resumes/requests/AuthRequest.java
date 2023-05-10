package com.example.resumes.requests;

import com.example.resumes.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class AuthRequest {

    @NotNull
    @Email
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private User.Role role;
}
