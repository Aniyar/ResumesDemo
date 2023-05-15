package com.example.resumes.graphql.resolver;

import com.example.resumes.exceptionHandler.ResumeNotFoundException;
import com.example.resumes.model.DocumentDetails;
import com.example.resumes.model.Resume;
import com.example.resumes.repository.JPA.ResumeRepository;
import com.example.resumes.service.ResumeService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class ResumeResolver implements GraphQLResolver<DocumentDetails> {
    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;

    public Resume resume(DocumentDetails documentDetails) throws ResumeNotFoundException, IOException {
        String username = documentDetails.getResumeUsername();
        log.info("Requesting resume data for documents username {}", username);
        Optional<Resume> optional = resumeRepository.findByUsername(username);
        if (optional.isPresent()){
            Resume res = optional.get();
            return Resume.builder()
                    .username(username)
                    .position(res.getPosition())
                    .salary(res.getSalary())
                    .fileData(resumeService.downloadCV(res.getId()).getBody())
                    .imageData(resumeService.downloadImage(res.getId()).getBody()).build();
        }else{
            return new Resume();
        }
    }
}
