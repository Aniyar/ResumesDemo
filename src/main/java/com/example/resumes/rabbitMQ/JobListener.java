package com.example.resumes.rabbitMQ;

import com.example.resumes.exceptionHandler.JobAPIException;
import com.example.resumes.service.JobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.resumes.constants.Constant.queueName;
import static com.example.resumes.constants.JobConstant.N_JOBS_FROM_API;
import static com.example.resumes.constants.JobConstant.SORT_BY;

@Service
@Slf4j
@AllArgsConstructor
public class JobListener {
    private final JobService jobService;

    @RabbitListener(queues = {queueName})
    public void onMessage(byte[] message) throws JobAPIException, IOException {
        jobService.getJobsFromAPI(N_JOBS_FROM_API, SORT_BY);

    }
}
