package com.example.resumes.util;

import lombok.experimental.UtilityClass;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@UtilityClass
public class ContentUtils {
    private static final List<String> NOT_ALLOWED_WORDS = Arrays.asList(
            "politics",
            "terrorism",
            "murder",
            "террорист",
            "убийца"
    );

    public static List<ObjectError> getContentErrorsFrom(String content) {
        return Arrays.stream(content.split("\\s"))
                .filter(NOT_ALLOWED_WORDS::contains)
                .map(notAllowedWord -> new ObjectError(notAllowedWord, "is not appropriate"))
                .collect(Collectors.toList());
    }
}