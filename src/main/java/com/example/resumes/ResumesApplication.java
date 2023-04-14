package com.example.resumes;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@AllArgsConstructor
@EnableMongoRepositories(basePackages = "com.example.resumes.job.repository")
@EnableJpaRepositories(basePackages = "com.example.resumes.resume.repository")
public class ResumesApplication {
	public static void main(String[] args) {
		SpringApplication.run(ResumesApplication.class, args);
		
	}

}
