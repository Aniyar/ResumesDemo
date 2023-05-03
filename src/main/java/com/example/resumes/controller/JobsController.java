package com.example.resumes.controller;

import com.example.resumes.exceptionHandler.JobAPIException;
import com.example.resumes.repository.jobRepository.JobRepository;
import com.example.resumes.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/jobs")
public class JobsController {

    private final JobService jobService;
    private final JobRepository jobRepository;
    @GetMapping
    public ResponseEntity<?> getJobs() {
        return new ResponseEntity<>(jobRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{number}/{sort}")
    public ResponseEntity<?> getJobs(@PathVariable int number, @PathVariable String sort) throws JobAPIException, IOException {
        return new ResponseEntity<>(jobService.getJobsFromAPI(number, sort), HttpStatus.OK);
    }

}
