package com.takalobazar.admin.service;

import com.takalobazar.admin.config.UnauthorizedException;
import org.springframework.http.HttpStatusCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takalobazar.admin.domain.APIResponse.TypeReportsResponse;
import com.takalobazar.admin.domain.TypeReport;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TypeReportService {
    private final RestTemplate restTemplate;
    public TypeReportService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void updateTypeReport(TypeReport typeReport) {
        String url = Constants.API_URL.concat("/typeReports/").concat(typeReport.getId().toString());
        try {
            restTemplate.put(url, typeReport);
        } catch (UnauthorizedException e) {
            throw e;
        } catch (HttpStatusCodeException e) {
            HttpStatusCode statusCode = e.getStatusCode();
            String errorMessage = "Error API " + e.getMessage();
            if (statusCode.value() != 500 && statusCode.value() != 401) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode errorResponse = objectMapper.readTree(e.getResponseBodyAsString());
                    if (errorResponse.has("error")) {
                        errorMessage = errorResponse.get("error").asText();
                    }
                } catch (Exception ex) { }
            }
            throw new RuntimeException(errorMessage);
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void saveTypeReport(TypeReport typeReport) {
        String url = Constants.API_URL.concat("/typeReports");
        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("name", typeReport.getName());
        try {
            restTemplate.postForObject(url, requestPayload, Void.class);
        } catch (UnauthorizedException e) {
            throw e;
        } catch (HttpStatusCodeException e) {
            HttpStatusCode statusCode = e.getStatusCode();
            String errorMessage = "Error API " + e.getMessage();
            if (statusCode.value() != 500 && statusCode.value() != 401) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode errorResponse = objectMapper.readTree(e.getResponseBodyAsString());
                    if (errorResponse.has("error")) {
                        errorMessage = errorResponse.get("error").asText();
                    }
                } catch (Exception ex) {}
            }
            throw new RuntimeException(errorMessage);
        } catch (RestClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public TypeReportsResponse getTypeReports(int page, int size, String query) {
        String url = Constants.API_URL.concat("/typeReports");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("page", page)
                .queryParam("limit", size);

        if (query != null && !query.isEmpty()) {
            uriBuilder.queryParam("q", query);
        }

        String response = restTemplate.getForObject(uriBuilder.toUriString(), String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode dataNode = objectMapper.readTree(response).path("data");
            return objectMapper.treeToValue(dataNode, TypeReportsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteTypeReport(Integer id) {
        String url = Constants.API_URL.concat("/typeReport/").concat(id.toString());

        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
        } catch (UnauthorizedException e) {
            throw e;
        } catch (HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();
            String errorMessage = "Unknown error occurred";
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode messageNode = root.path("message");
                if (!messageNode.isMissingNode()) {
                    errorMessage = messageNode.asText();
                }
            } catch (Exception jsonException) {
                jsonException.printStackTrace();
            }
            throw new RuntimeException(errorMessage);
        }
    }
}
