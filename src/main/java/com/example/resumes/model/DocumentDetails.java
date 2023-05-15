package com.example.resumes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "document_details")
public class DocumentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="iin", nullable = false)
    private String iin;
    @Column(name="surname", nullable = false)
    private String surname;
    @Column(name="firstname", nullable = false)
    private String firstName;
    @Column(name="document_number", nullable = false)
    private String documentNumber;
    @Column(name="resume_username", nullable = false)
    private String resumeUsername;

    public String getFullName(){
        return surname + " " + firstName;
    }
}
