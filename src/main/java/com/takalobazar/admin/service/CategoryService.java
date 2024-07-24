package com.takalobazar.admin.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CategoryService {
    private final RestTemplate restTemplate;

    public CategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCategories() {
        String url = Constants.API_URL.concat("/categories");
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}