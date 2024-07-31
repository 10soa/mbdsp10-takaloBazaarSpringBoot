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

    public Dashboard getDashboard(String date1, String date2, String statut) {
        String url = Constants.API_URL.concat("/dashboard");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        if (date1 != null && !date1.isEmpty()) {
            uriBuilder.queryParam("date1", date1);
        }
        if (date2 != null && !date2.isEmpty()) {
            uriBuilder.queryParam("date2", date2);
        }
        if (statut != null && !statut.isEmpty()) {
            uriBuilder.queryParam("status", statut);
        }
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
