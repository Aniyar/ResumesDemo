package com.example.resumes.exceptionHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FileUploadException extends Exception{
    @Override
    public String getMessage() {
        return "Something went wrong while uploading the file. The file exceeds the maximum file size or is corrupt";
    }
}
