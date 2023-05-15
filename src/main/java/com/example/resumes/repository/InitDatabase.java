package com.example.resumes.repository;

import com.example.resumes.model.DocumentDetails;
import com.example.resumes.model.Resume;
import com.example.resumes.repository.JPA.DocumentDetailsRepository;
import com.example.resumes.repository.JPA.ResumeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@AllArgsConstructor
public class InitDatabase {
    private final ResumeRepository resumeRepository;
    private final DocumentDetailsRepository ddRepository;
    @PostConstruct
    void initDatabase() {
        if (ddRepository.count() == 0) {
            DocumentDetails docs1 = new DocumentDetails();
            docs1.setIin("030727651379");
            docs1.setSurname("Durmagambetova");
            docs1.setFirstName("Aniyar");
            docs1.setDocumentNumber("047562234");
            docs1.setResumeUsername("Aniyar");
            ddRepository.save(docs1);


            DocumentDetails docs2 = new DocumentDetails();
            docs2.setIin("000727651379");
            docs2.setSurname("Surname");
            docs2.setFirstName("Timur");
            docs2.setDocumentNumber("0407276513");
            docs2.setResumeUsername("Timur");
            ddRepository.save(docs2);

            DocumentDetails docs3 = new DocumentDetails();
            docs3.setIin("000443263565");
            docs3.setSurname("Yessentay");
            docs3.setFirstName("Bota");
            docs3.setDocumentNumber("0407233513");
            docs3.setResumeUsername("Bota");
            ddRepository.save(docs3);
        }


        if (resumeRepository.count() == 0) {
            resumeRepository.saveAll(List.of(
                    new Resume("Aniyar", "alabs", "Intern", Long.valueOf(100)),
                    new Resume("Timur", "alabs", "Middle", Long.valueOf(1000)),
                    new Resume("Bota", "hospital", "camel", Long.valueOf(400))));


        }

    }
}
