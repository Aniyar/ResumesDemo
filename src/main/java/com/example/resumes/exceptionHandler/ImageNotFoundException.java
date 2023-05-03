package com.example.resumes.exceptionHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ImageNotFoundException extends Exception{
    private Long resumeId;
    @Override
    public String getMessage() {
        return "Image for resume with id '" + resumeId + "' not found";
    }
}
