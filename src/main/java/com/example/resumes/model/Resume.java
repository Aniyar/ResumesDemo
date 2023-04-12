package com.example.resumes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "position", nullable = false)
    private String position;
    @Column(name = "company", nullable = true)
    private String company;
    @Column(name = "comment", nullable = true)
    private String comment;
    @Column(name = "salary", nullable = false)
    private Long salary;

    private String imageName;
    private String imageType;
    @Lob
    @Column(name = "imagedata")
    private byte[] imageData;
    
    private String fileName;
    private String fileType;
    @Lob
    @Column(name = "filedata")
    private byte[] fileData;
    
    public Resume(String username, String company, String position, Long salary){
        this.username = username;
        this.company = company;
        this.position = position;
        this.salary = salary;
    }

    public Resume(String username, String position, Long salary){
        this.username = username;
        this.position = position;
        this.salary = salary;
    }
}
