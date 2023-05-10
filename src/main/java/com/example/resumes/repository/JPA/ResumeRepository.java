package com.example.resumes.repository.JPA;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.resumes.model.Resume;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long>{

}
