package com.example.resumes.util;

import com.example.resumes.exceptionHandler.JobAPIException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@UtilityClass
public class APIUtil {

    public String getResponseStringFromAPI(String urlString) throws JobAPIException, IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() == 200) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())))) {
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
            }
            return sb.toString();
        } else {
            throw new JobAPIException("api didn't respond");
        }
    }

    public JsonNode convertResponseToJsonNode(String jsonString) throws JsonProcessingException {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
        JsonNode arrayNode = objectMapper.readTree(jsonString).get("results");
        return arrayNode;
    }
}
