package com.takalobazar.admin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takalobazar.admin.domain.APIResponse.CategoriesResponse;
import com.takalobazar.admin.domain.Category;
import com.takalobazar.admin.domain.Dashboard;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {
    private final RestTemplate restTemplate;

    public DashboardService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Dashboard getDashboard() {
        String url = Constants.API_URL.concat("/dashboard");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("status", "Accepted");

        String response = restTemplate.getForObject(uriBuilder.toUriString(), String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Dashboard dashboardResponse = null;
        try {
            dashboardResponse = objectMapper.readValue(response, Dashboard.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dashboardResponse;
    }

}
