package com.example.resumes.graphql.resolver.mutation;

import com.example.resumes.model.DocumentDetails;
import com.example.resumes.repository.JPA.DocumentDetailsRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddDocumentsResolver implements GraphQLMutationResolver {
    private final DocumentDetailsRepository ddRepository;

    public DocumentDetails addDocuments(String firstName, String surname, String iin, String documentNumber, String resumeUsername) {
        DocumentDetails docs = DocumentDetails.builder()
                                                .firstName(firstName)
                                                .surname(surname)
                                                .iin(iin)
                                                .documentNumber(documentNumber)
                                                .resumeUsername(resumeUsername)
                                                .build();
        ddRepository.save(docs);
        return docs;
    }

}
