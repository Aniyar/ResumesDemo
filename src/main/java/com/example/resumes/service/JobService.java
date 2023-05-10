package com.example.resumes.service;

import com.example.resumes.exceptionHandler.JobAPIException;
import com.example.resumes.model.Job;
import com.example.resumes.requests.CreateJobRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface JobService {
    Iterable<Job> getJobsFromAPI(Integer resultsPerPage, String sortBy) throws JobAPIException, IOException;

    ResponseEntity<Job> createJob(CreateJobRequest jobRequest);
}
