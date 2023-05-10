package com.example.resumes.exceptionHandler.globalExceptionHandler;

import com.example.resumes.exceptionHandler.*;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ ResumeNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleResumeNotFound(ResumeNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ ContentNotAllowedException.class})
    public final ResponseEntity<ErrorResponse> handleContentNotAllowed(ContentNotAllowedException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_ACCEPTABLE.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_ACCEPTABLE);
    }



    @ExceptionHandler({ JobAPIException.class})
    public final ResponseEntity<ErrorResponse> handleJobAPIException(JobAPIException ex) {
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ FileUploadException.class})
    public final ResponseEntity<ErrorResponse> handleFileUploadException(JobAPIException ex) {
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({FileSizeLimitExceededException.class})
    public final ResponseEntity<ErrorResponse> handleFileSizeLimitExceeded(FileSizeLimitExceededException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.PAYLOAD_TOO_LARGE.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException .class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}