package com.nagarro.si.um.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nagarro.si.common.dto.CandidateDto;
import com.nagarro.si.um.exception.CandidateServiceClientRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    private static final String CANDIDATES_EMAIL_PATH_FORMAT = "%s/candidates/emails/%s";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${candidate.service.url}")
    private String candidateServiceUrl;

    @Autowired
    public CandidateServiceClientUtil(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;

        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public Optional<CandidateDto> getCandidateByEmail(String email) {
        try {
            URI uri = new URI(String.format(CANDIDATES_EMAIL_PATH_FORMAT, candidateServiceUrl, email));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpStatus.OK.value()) {
                return Optional.empty();
            }

            return Optional.of(objectMapper.readValue(response.body(), CandidateDto.class));
        } catch (URISyntaxException | IOException | InterruptedException exception) {
            throw new CandidateServiceClientRequestException(String.format("Failed to fetch candidate by email: %s", email));
        }
    }
}