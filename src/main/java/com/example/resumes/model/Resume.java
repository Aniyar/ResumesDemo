package com.example.resumes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "position", nullable = false)
    private String position;
    @Column(name = "company")
    private String company;
    @Column(name = "comment")
    private String comment;
    @Column(name = "salary", nullable = false)
    private Long salary;
    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_type")
    private String imageType;
    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    private String fileType;
    @Lob
    @Column(name = "file_data")
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
