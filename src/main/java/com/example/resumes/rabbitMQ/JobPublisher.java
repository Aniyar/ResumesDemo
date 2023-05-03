package com.example.resumes.rabbitMQ;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.example.resumes.constants.Constant.queueName;
import static com.example.resumes.constants.Constant.topicExchangeName;

@Service
@Slf4j
@AllArgsConstructor
public class JobPublisher {
    private final RabbitTemplate rabbitTemplate;
    @Scheduled(fixedRate = 1000000, initialDelay = 0)
    public void publishJob(){
        log.info("");
        System.out.println("Sending scheduled message...");
        rabbitTemplate.convertAndSend(topicExchangeName, queueName, "");
    }

}
