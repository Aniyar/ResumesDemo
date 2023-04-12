package com.example.resumes.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.resumes.model.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long>{

}
