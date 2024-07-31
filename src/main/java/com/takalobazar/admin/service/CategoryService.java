package com.takalobazar.admin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takalobazar.admin.config.UnauthorizedException;
import com.takalobazar.admin.domain.APIResponse.CategoriesResponse;
import com.takalobazar.admin.domain.Category;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import com.takalobazar.admin.domain.Category;
import com.takalobazar.admin.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CategoryService(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = mapper;
    }

    public Category getCategoryById(Integer id) {
        String url = Constants.API_URL.concat("/category/").concat(id.toString());
        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode categoryNode = root.path("category");
            return objectMapper.treeToValue(categoryNode, Category.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Category> findAllCategory() {
        String url = Constants.API_URL.concat("/categories");
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String responseBody = response.getBody();
        List<Category> rep = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode categoriesNode = root.path("data").path("categories");

            if (categoriesNode.isArray()) {
                for (JsonNode categoryNode : categoriesNode) {
                    Category category = objectMapper.treeToValue(categoryNode, Category.class);
                    rep.add(category);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rep;
    }

    public void updateCategory(Category category) {
        String url = Constants.API_URL.concat("/categories/").concat(category.getId().toString());

        try {
            restTemplate.put(url, category);
        } catch (HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();
            String errorMessage = "Unknown error occurred";
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode messageNode = root.path("error");
                if (!messageNode.isMissingNode()) {
                    errorMessage = messageNode.asText();
                }
            } catch (Exception jsonException) {
                jsonException.printStackTrace();
            }
            throw new RuntimeException(errorMessage);
        }
    }

    public void deleteCategory(Integer id) {
        String url = Constants.API_URL.concat("/category/").concat(id.toString());

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

    public void saveCategory(Category category) {
        String url = Constants.API_URL.concat("/categories");
        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("category", category.getName());

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestPayload, String.class);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Unexpected response status: " + responseEntity.getStatusCode());
            }
        } catch (HttpStatusCodeException e) {
            String responseBody = e.getResponseBodyAsString();
            String errorMessage = "Unknown error occurred";
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode messageNode = root.path("error");
                if (!messageNode.isMissingNode()) {
                    errorMessage = messageNode.asText();
                }
            } catch (Exception jsonException) {
                jsonException.printStackTrace();
            }
            throw new RuntimeException(errorMessage);
        }
    }

    public CategoriesResponse getCategories(int page, int size, String query) {
        String url = Constants.API_URL.concat("/categories");
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
            return objectMapper.treeToValue(dataNode, CategoriesResponse.class);
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}