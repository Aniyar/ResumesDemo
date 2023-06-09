package com.example.resumes.service;

import com.example.resumes.exceptionHandler.FileUploadException;
import com.example.resumes.exceptionHandler.ResumeNotFoundException;
import com.example.resumes.model.Resume;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ResumeService {
    Resume uploadImage(Long resumeId, MultipartFile file) throws IOException;
    ResponseEntity<byte[]> downloadImage(Long resumeId) throws IOException, ResumeNotFoundException;
    ResponseEntity<Resume> uploadCV(Long resumeId, MultipartFile file) throws IOException, ResumeNotFoundException, FileUploadException;
    ResponseEntity<byte[]> downloadCV(Long resumeId) throws ResumeNotFoundException;
    ResponseEntity<Resume> getResumeByID(Long id) throws ResumeNotFoundException;
}
