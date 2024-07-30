package com.nagarro.si.um.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nagarro.si.common.dto.CandidateDto;
import com.nagarro.si.um.exception.CandidateServiceException;
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
public class CandidateServiceClientImpl implements CandidateServiceClient{

    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${candidate.service.url}")
    private String candidateServiceUrl;

    @Override
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

            return Optional.of(objectMapper.registerModule(new JavaTimeModule()).readValue(response.body(), CandidateDto.class));
        } catch (URISyntaxException | IOException | InterruptedException exception) {
            throw new CandidateServiceException(String.format("Failed to fetch candidate by email: %s", email));
        }
    }
}
