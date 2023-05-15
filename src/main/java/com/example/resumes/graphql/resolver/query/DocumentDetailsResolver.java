package com.example.resumes.graphql.resolver.query;

import com.example.resumes.model.DocumentDetails;
import com.example.resumes.repository.JPA.DocumentDetailsRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@AllArgsConstructor
public class DocumentDetailsResolver implements GraphQLQueryResolver {
    private final DocumentDetailsRepository documentDetailsRepository;

    public DocumentDetails documentDetails(String id) {
        Optional<DocumentDetails> optional = documentDetailsRepository.findById(Long.parseLong(id));
        if (optional.isPresent()){
            DocumentDetails docs = optional.get();
            return DocumentDetails.builder()
                    .id(Long.valueOf(id))
                    .surname(docs.getSurname())
                    .firstName(docs.getFirstName())
                    .iin(docs.getIin())
                    .documentNumber(docs.getDocumentNumber())
                    .resumeUsername(docs.getResumeUsername())
                    .build();
        }
        return new DocumentDetails();
    }

    public List<DocumentDetails> getAllDocuments(){
        return documentDetailsRepository.findAll();
    }
}
