package com.example.resumes.exceptionHandler;

import lombok.AllArgsConstructor;
import org.springframework.validation.ObjectError;

import java.util.List;

@AllArgsConstructor
public class ContentNotAllowedException extends Exception {
    List<ObjectError> errors;
    public List<ObjectError> getErrors() {
        return errors;
    }
}
