package com.example.resumes.exceptionHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResumeNotFoundException extends Exception {
    private Long resumeId;
    @Override
    public String getMessage() {
        return "Resume of user '" + resumeId + "' not found";
    }
}
