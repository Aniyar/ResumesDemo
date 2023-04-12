package com.example.resumes;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.example.resumes.model.Resume;
import com.example.resumes.repository.ResumeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@AllArgsConstructor
public class ResumesApplication {


	private final ResumeRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ResumesApplication.class, args);
		
	}

}
