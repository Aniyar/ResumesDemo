package com.example.resumes.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.resumes.model.Resume;

public interface ResumeService {
    public Resume uploadImage(Resume resume, MultipartFile file) throws IOException ;
    public byte[] downloadImage(Long resumeId) throws IOException;
    public Resume uploadCV(Resume resume, MultipartFile file) throws IOException;
    public byte[] downloadCV(Long resumeId);
}
