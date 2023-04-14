package com.example.resumes.resume.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.resumes.resume.model.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long>{

}
