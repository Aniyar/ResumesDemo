package com.example.resumes.exceptionHandler;

import com.example.resumes.util.ImageUtil;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.resumes.constants.Constant.PATH_TO_NOJPG;
import static com.example.resumes.constants.Constant.PATH_TO_NOPDF;

@ControllerAdvice
public class GlobalExceptionHandler {
    /** Provides handling for exceptions throughout this service. */
    @ExceptionHandler({ ResumeNotFoundException.class, ContentNotAllowedException.class, CVNotFoundException.class, ImageNotFoundException.class })
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof ResumeNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ResumeNotFoundException unfe = (ResumeNotFoundException) ex;
            return handleBasicException(unfe, headers, status, request);
        } else if (ex instanceof ContentNotAllowedException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ContentNotAllowedException cnae = (ContentNotAllowedException) ex;

            return handleContentNotAllowedException(cnae, headers, status, request);
        } else if (ex instanceof CVNotFoundException){
            HttpStatus status = HttpStatus.NOT_FOUND;
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().build());
            CVNotFoundException cnfe = (CVNotFoundException) ex;
            return handleCVNotFoundException(cnfe, headers, status, request);
        } else if (ex instanceof ImageNotFoundException){
            HttpStatus status = HttpStatus.NOT_FOUND;
            headers.setContentType(MediaType.IMAGE_PNG);
            ImageNotFoundException imfe = (ImageNotFoundException) ex;
            return handleImageNotFoundException(imfe, headers, status, request);
        } else if (ex instanceof JobAPIException) {
            HttpStatus status = HttpStatus.BAD_GATEWAY;
            JobAPIException japie = (JobAPIException) ex;
            return handleBasicException(japie, headers, status, request);
        } else if (ex instanceof FileUploadException) {
            HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
            return handleBasicException(ex, headers, status, request);
        }
        else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    private ResponseEntity handleImageNotFoundException(ImageNotFoundException imfe, HttpHeaders headers, HttpStatus status, WebRequest request) {
        byte[] imgData = ImageUtil.getBytes(PATH_TO_NOJPG);
        return new ResponseEntity<>(imgData, headers, status);
    }

    private ResponseEntity handleCVNotFoundException(CVNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        byte[] pdfContent = ImageUtil.getBytes(PATH_TO_NOPDF);
        return new ResponseEntity<>(pdfContent, headers, status);
    }

    /** Customize the response for UserNotFoundException. */
    protected ResponseEntity<ApiError> handleBasicException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), headers, status, request);
    }

    /** Customize the response for ContentNotAllowedException. */
    protected ResponseEntity<ApiError> handleContentNotAllowedException(ContentNotAllowedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessages = ex.getErrors()
                .stream()
                .map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage())
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, new ApiError(errorMessages), headers, status, request);
    }

    /** A single place to customize the response body of all Exception types. */
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}