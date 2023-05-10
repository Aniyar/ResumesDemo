package com.example.resumes;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@AllArgsConstructor
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.example.resumes.repository.JPA")
@EnableMongoRepositories(basePackages = "com.example.resumes.repository.Mongo")
public class ResumesApplication {
	public static void main(String[] args) {
		SpringApplication.run(ResumesApplication.class, args);
	}

}
