package com.example.resumes.repository.JPA;


import com.example.resumes.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long>{
    @Transactional
    Optional<Resume> findByUsername(String username);

}
