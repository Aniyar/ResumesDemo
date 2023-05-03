package com.example.resumes.repository.jobRepository;

import com.example.resumes.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {
    Optional<Job> findByAPIid (String APIid);
}
