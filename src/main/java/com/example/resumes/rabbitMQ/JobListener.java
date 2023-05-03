package com.example.resumes.rabbitMQ;

import com.example.resumes.exceptionHandler.JobAPIException;
import com.example.resumes.service.JobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.resumes.constants.Constant.queueName;

@Service
@Slf4j
@AllArgsConstructor
public class JobListener {
    private final JobService jobService;

    @RabbitListener(queues = {queueName})
    public void onMessage(byte[] message) throws JobAPIException, IOException {
        jobService.getJobsFromAPI(50, "date");

    }
}
