package com.example.resumes.exceptionHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CVNotFoundException extends Exception{
    private String username;
    @Override
    public String getMessage() {
        return "CV of user '" + username + "' not found";
    }
}
