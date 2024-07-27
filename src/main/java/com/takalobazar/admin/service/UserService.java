package com.takalobazar.admin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takalobazar.admin.domain.User;
import com.takalobazar.admin.domain.UsersResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UsersResponse getUsers(int page, int size, String q, String gender, String type) {
        String url = Constants.API_URL.concat("/users");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("page", page)
                .queryParam("limit", size)
                .queryParam("search", q)
                .queryParam("gender", gender)
                .queryParam("type", type);

        String response = restTemplate.getForObject(uriBuilder.toUriString(), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, UsersResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public User getUserById(Integer id) {
        String url = Constants.API_URL.concat("/user/").concat(id.toString());
        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode userNode = root.path("user");
            return objectMapper.treeToValue(userNode, User.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUser(User user, String base64Image) {
        String url = Constants.API_URL.concat("/user/").concat(user.getId().toString());
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("username", user.getUsername());
        requestPayload.put("email", user.getEmail());
        requestPayload.put("first_name", user.getFirst_name());
        requestPayload.put("last_name", user.getLast_name());
        requestPayload.put("gender", user.getGender());

        if (base64Image != null) {
            requestPayload.put("image", base64Image);
        }

        restTemplate.put(url, requestPayload);
    }

    public void deleteUser(Integer id) {
        String url = Constants.API_URL.concat("/user/").concat(id.toString());

        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
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
