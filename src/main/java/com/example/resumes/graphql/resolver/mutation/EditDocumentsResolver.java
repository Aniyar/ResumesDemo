package com.example.resumes.graphql.resolver.mutation;

import com.example.resumes.model.DocumentDetails;
import com.example.resumes.repository.JPA.DocumentDetailsRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class EditDocumentsResolver implements GraphQLMutationResolver {
    private final DocumentDetailsRepository ddRepository;
    public DocumentDetails editDocuments(String id, String firstName, String surname, String iin, String documentNumber, String resumeUsername) {
        Optional<DocumentDetails> optional = ddRepository.findById(Long.valueOf(id));
        if (optional.isPresent()){
            DocumentDetails docs = optional.get();
            DocumentDetails update = DocumentDetails.builder()
                                                    .firstName(firstName)
                                                    .surname(surname)
                                                    .iin(iin)
                                                    .documentNumber(documentNumber)
                                                    .resumeUsername(resumeUsername)
                                                    .build();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT)
                    .setPropertyCondition(ctx -> ctx.getSource() != null);
            modelMapper.map(update, docs);
            ddRepository.save(docs);
            return docs;
        }
        else{
            return new DocumentDetails();
        }
    }

}
