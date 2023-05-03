package com.example.resumes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;
    public String getMessage(String key, Locale locale){
        return messageSource.getMessage(key,null,locale);
    }
}
