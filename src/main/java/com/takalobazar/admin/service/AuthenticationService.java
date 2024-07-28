package com.takalobazar.admin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private final RestTemplate restTemplate;
    private final HttpSession httpSession;

    public AuthenticationService(RestTemplate restTemplate, HttpSession httpSession) {
        this.restTemplate = restTemplate;
        this.httpSession = httpSession;
    }

    public boolean login(String username, String password) throws Exception {
        String url = Constants.API_URL.concat("/auth/admin/login");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("username", username);
        requestPayload.put("password", password);

        try {
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestPayload);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    uriBuilder.toUriString(),
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String token = jsonNode.path("admin").path("token").asText();
                httpSession.removeAttribute("NEEDS_REAUTHENTICATION");
                httpSession.setAttribute("AUTH_TOKEN", token);
                return true;
            } else {
                throw new Exception(extractErrorMessage(responseEntity.getBody()));
            }
        } catch (HttpStatusCodeException e) {
            throw new Exception(extractErrorMessage(e.getResponseBodyAsString()), e);
        } catch (Exception e) {
            throw new Exception("An error occurred during login: " + e.getMessage(), e);
        }
    }

    private String extractErrorMessage(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.path("error").asText("An unexpected error occurred.");
        } catch (Exception e) {
            return "An unexpected error occurred while processing the error response.";
        }
    }
}
