package com.example.resumes.repository.resumeRepository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.resumes.model.Resume;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long>{

}
