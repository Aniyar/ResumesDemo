package com.example.resumes.service.impl;

import com.example.resumes.exceptionHandler.JobAPIException;
import com.example.resumes.model.Job;
import com.example.resumes.model.User;
import com.example.resumes.repository.JPA.UserRepository;
import com.example.resumes.repository.Mongo.JobRepository;
import com.example.resumes.requests.CreateJobRequest;
import com.example.resumes.service.JobService;
import com.example.resumes.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.resumes.constants.JobConstant.*;
import static com.example.resumes.util.APIUtil.convertResponseToJsonNode;
import static com.example.resumes.util.APIUtil.getResponseStringFromAPI;


@Service
@Slf4j
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final MessageService messageService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public String getJobApiUrlString(Integer resultsPerPage, String sortby){
        return String.format(messageService.getMessage(ADZUNA_API_URL, Locale.ENGLISH),messageService.getMessage(API_APPLICATION_ID, Locale.ENGLISH),messageService.getMessage(API_KEY, Locale.ENGLISH),resultsPerPage,sortby);

    }
    public Iterable<Job> getJobsFromAPI(Integer resultsPerPage, String sortBy) throws JobAPIException, IOException {
        String apiUrl = getJobApiUrlString(resultsPerPage, sortBy);
        String response = getResponseStringFromAPI(apiUrl);
        JsonNode arrayNode = convertResponseToJsonNode(response);
        List<Job> jobs = new ArrayList<>() {};
        List<Job> oldJobs = jobRepository.findAll();
        if (arrayNode.isArray()) {
            for (JsonNode jsonNode : arrayNode) {
                Job newJob = new Job();
                newJob.setAPIid(jsonNode.get("id").asText());
                if (oldJobs.stream().filter(job -> job.getAPIid().contains(newJob.getAPIid())).collect(Collectors.toList()).isEmpty()){
                    try {
                        newJob.setPosition(jsonNode.get("title").asText());
                        newJob.setCategory(jsonNode.get("category").get("label").asText());
                        newJob.setCompany(jsonNode.get("company").get("display_name").asText());
                        newJob.setSalary((long) jsonNode.get("salary_max").asDouble());
                        newJob.setLocation(jsonNode.get("location").get("display_name").asText());
                        newJob.setComment(jsonNode.get("description").asText());
                        jobs.add(newJob);
                    }catch (Exception ex){
                        log.info("jsonNode was not correctly formatted");
                    }
                }
            }
        }
        return jobRepository.saveAll(jobs);
    }


    public ResponseEntity<Job> createJob(CreateJobRequest jobRequest){
        String token = jobRequest.getToken();
        String email = jwtTokenUtil.getUsernameFromToken(token);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = optionalUser.get();
        if (!user.getRole().equals(User.Role.HR)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // return forbidden status code if user doesn't have the required role
        }
        Job savedJob = jobRepository.save(convertJobRequestToJob(jobRequest));
        return ResponseEntity.ok(savedJob);
    }

    public Job convertJobRequestToJob(CreateJobRequest jobRequest){
        Job newJob = new Job();
        newJob.setPosition(jobRequest.getPosition());
        newJob.setCompany(jobRequest.getCompany());
        newJob.setSalary(jobRequest.getSalary());
        newJob.setCategory(jobRequest.getCategory());
        newJob.setComment(jobRequest.getComment());
        newJob.setLocation(jobRequest.getLocation());
        newJob.setAPIid("created by HR");
        return newJob;
    }

}
