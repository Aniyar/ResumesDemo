package com.example.resumes.exceptionHandler;

import lombok.AllArgsConstructor;
import org.springframework.validation.ObjectError;

import java.util.List;

@AllArgsConstructor
public class ContentNotAllowedException extends Exception {
    List<ObjectError> errors;
    @Override
    public String getMessage() { return errors.toString();}

//    @Override
//    public String toString() {
//        foreach (ObjectError error: errors):
//             ) {
//
//        }
//        return super.toString();
//    }
}
