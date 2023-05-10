package com.example.resumes.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateJobRequest {
    @NotNull
    private String token;
    @NotNull
    private String position;
    @NotNull
    private String category;
    @NotNull
    private String company;
    @NotNull
    private Long salary;
    @NotNull
    private String location;
    @NotNull
    private String comment;
}
