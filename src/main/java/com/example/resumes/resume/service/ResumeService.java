package com.example.resumes.resume.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.resumes.resume.model.Resume;

public interface ResumeService {
    Resume uploadImage(Long resumeId, MultipartFile file) throws IOException;
    byte[] downloadImage(Long resumeId) throws IOException;
    Resume uploadCV(Resume resume, MultipartFile file) throws IOException;
    byte[] downloadCV(Long resumeId);
}
