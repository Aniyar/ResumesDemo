package com.example.resumes.repository.JPA;

import com.example.resumes.model.DocumentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentDetailsRepository extends JpaRepository<DocumentDetails, Long> {
    Optional<DocumentDetails> findByIin(String iin);
    Optional<DocumentDetails> findByDocumentNumber(String documentNumber);
}
