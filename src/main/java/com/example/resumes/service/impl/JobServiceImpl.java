package com.example.resumes.service.impl;

import com.example.resumes.exceptionHandler.JobAPIException;
import com.example.resumes.model.Job;
import com.example.resumes.repository.jobRepository.JobRepository;
import com.example.resumes.service.JobService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.example.resumes.constants.JobConstant.*;
import static com.example.resumes.util.APIUtil.*;


@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final MessageService messageService;
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
                    newJob.setPosition(jsonNode.get("title").asText());
                    newJob.setCategory(jsonNode.get("category").get("label").asText());
                    newJob.setCompany(jsonNode.get("company").get("display_name").asText());
                    newJob.setSalary((long) jsonNode.get("salary_max").asDouble());
                    newJob.setLocation(jsonNode.get("location").get("display_name").asText());
                    newJob.setComment(jsonNode.get("description").asText());
                    jobs.add(newJob);
                }
            }
        }
        return jobRepository.saveAll(jobs);
    }
}
