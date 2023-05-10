package com.example.resumes.controller;

import com.example.resumes.exceptionHandler.JobAPIException;
import com.example.resumes.model.Job;
import com.example.resumes.repository.Mongo.JobRepository;
import com.example.resumes.requests.CreateJobRequest;
import com.example.resumes.service.JobService;
import com.example.resumes.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/jobs")
public class JobsController {

    private final JobService jobService;
    private final JobRepository jobRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public ResponseEntity<Iterable<Job>> getJobs() {
        return new ResponseEntity<>(jobRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{number}/{sort}")
    public ResponseEntity<Iterable<Job>> getJobs(@PathVariable int number, @PathVariable String sort) throws JobAPIException, IOException {
        return new ResponseEntity<>(jobService.getJobsFromAPI(number, sort), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Job> createJob(@RequestBody @Valid CreateJobRequest jobRequest) {
        return jobService.createJob(jobRequest);
    }


}
