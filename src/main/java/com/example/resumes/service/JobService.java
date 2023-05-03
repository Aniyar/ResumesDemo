package com.example.resumes.service;

import com.example.resumes.exceptionHandler.JobAPIException;
import com.example.resumes.model.Job;

import java.io.IOException;

public interface JobService {
    Iterable<Job> getJobsFromAPI(Integer resultsPerPage, String sortBy) throws JobAPIException, IOException;
}
