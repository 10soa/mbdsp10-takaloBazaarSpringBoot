package com.takalobazar.admin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takalobazar.admin.domain.Category;
import com.takalobazar.admin.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CategoryService(RestTemplate restTemplate,ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = mapper;
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
}