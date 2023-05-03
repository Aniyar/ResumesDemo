package com.example.resumes.exceptionHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResumeNotFoundException extends Exception {
    private Long resumeId;

    public static ResumeNotFoundException createWith(Long resumeId) {
        return new ResumeNotFoundException(resumeId);
    }

    @Override
    public String getMessage() {
        return "Resume of user '" + resumeId + "' not found";
    }
}
