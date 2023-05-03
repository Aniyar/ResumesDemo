package com.example.resumes.model;

import java.util.List;

import com.example.resumes.repository.resumeRepository.ResumeRepository;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import javax.annotation.PostConstruct;


@Component
@AllArgsConstructor
public class LoadDatabase {
    private final ResumeRepository resumeRepository;

    @PostConstruct
    private void initDatabase() {
        if (resumeRepository.count() == 0) {
            resumeRepository.saveAll(List.of(
                    new Resume("Aniyar", "Intern", Long.valueOf(1)),
                    new Resume("Timur", "alabs", "Middle", Long.valueOf(1000)),
                    new Resume("Bota", "hospital", "camel", Long.valueOf(400))));
        }
    }
    
}
