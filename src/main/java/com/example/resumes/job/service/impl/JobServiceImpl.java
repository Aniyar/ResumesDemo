package com.example.resumes.job.service.impl;

import com.example.resumes.constants.Constant;
import com.example.resumes.job.model.Job;
import com.example.resumes.job.repository.JobRepository;
import com.example.resumes.job.service.JobService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    public Iterable<Job> getJobsFromAPI(int resultsPerPage, String sortBy){
        try {
            String endpoint = "https://api.adzuna.com/v1/api/jobs/us/search/1"
                    + "?app_id=" + Constant.API_APPLICATION_ID
                    + "&app_key=" + Constant.API_KEY
                    + "&results_per_page=" + resultsPerPage
                    + "&sort_by=" + sortBy;

            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }


            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String jsonString = sb.toString();
            JsonFactory jsonFactory = new JsonFactory();
            ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
            JsonNode arrayNode = objectMapper.readTree(jsonString).get("results");

            conn.disconnect();
            List<Job> jobs = new ArrayList<Job>() {
            };
            if (arrayNode.isArray()) {
                for (JsonNode jsonNode : arrayNode) {
                    Job newJob = new Job();
                    newJob.setPosition(jsonNode.get("title").asText());
                    newJob.setCategory(jsonNode.get("category").get("label").asText());
                    newJob.setCompany(jsonNode.get("company").get("display_name").asText());
                    newJob.setSalary((long) jsonNode.get("salary_max").asDouble());
                    newJob.setLocation(jsonNode.get("location").get("display_name").asText());
                    newJob.setComment(jsonNode.get("description").asText());
                    jobs.add(newJob);
                }
            }
            return jobRepository.saveAll(jobs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
