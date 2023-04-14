package com.example.resumes.job.service;

import com.example.resumes.job.model.Job;

public interface JobService {
    Iterable<Job> getJobsFromAPI(int resultsPerPage, String sortBy);
}
