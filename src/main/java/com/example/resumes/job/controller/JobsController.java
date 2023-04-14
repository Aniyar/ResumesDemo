package com.example.resumes.job.controller;

import com.example.resumes.job.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/jobs")
public class JobsController {

    private final JobService jobService;
    @GetMapping
    public ResponseEntity<?> getJobs() {
        return new ResponseEntity<>(jobService.getJobsFromAPI(5, "date"), HttpStatus.OK);
    }

    @GetMapping("/{number}/{sort}")
    public ResponseEntity<?> getJobs(@PathVariable int number, @PathVariable String sort) {
        return new ResponseEntity<>(jobService.getJobsFromAPI(number, sort), HttpStatus.OK);
    }

}
