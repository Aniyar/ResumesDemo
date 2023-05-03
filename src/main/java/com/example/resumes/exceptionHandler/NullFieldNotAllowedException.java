package com.example.resumes.exceptionHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NullFieldNotAllowedException extends Exception{
    private String fieldName;
    @Override
    public String getMessage() {
        return "Field " + fieldName + " is not allowed to be null";
    }
}
