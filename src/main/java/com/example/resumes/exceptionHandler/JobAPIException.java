package com.example.resumes.exceptionHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JobAPIException extends Exception{
    private String message;
    @Override
    public String getMessage() { return message == null ? "Something went wrong while processing job from API" : message;
    }
}
