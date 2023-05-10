package com.example.resumes.repository.Mongo;

import com.example.resumes.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {
    Optional<Job> findByAPIid (String APIid);
}
