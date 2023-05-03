package com.example.resumes.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Document("job")
public class Job {
    @Id
    private String  id;
    private String APIid;
    private String position;
    private String category;
    private String company;
    private Long salary;
    private String location;
    private String comment;

    public Job(){
        super();
    }
}
