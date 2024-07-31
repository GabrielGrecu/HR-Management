package com.nagarro.si.um.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nagarro.si.common.dto.CandidateDto;
import com.nagarro.si.um.exception.CandidateServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Component
public class CandidateServiceClientUtil {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String candidateServiceUrl;

    @Autowired
    public CandidateServiceClientUtil(HttpClient httpClient, ObjectMapper objectMapper, @Value("${candidate.service.url}") String candidateServiceUrl) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.candidateServiceUrl = candidateServiceUrl;

        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public Optional<CandidateDto> getCandidateByEmail(String email) {
        try {
            URI uri = new URI(String.format("%s/candidates/email/%s", candidateServiceUrl, email));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return Optional.empty();
            }

            return Optional.of(objectMapper.readValue(response.body(), CandidateDto.class));
        } catch (URISyntaxException | IOException | InterruptedException exception) {
            throw new CandidateServiceException(String.format("Failed to fetch candidate by email: %s", email));
        }
    }
}