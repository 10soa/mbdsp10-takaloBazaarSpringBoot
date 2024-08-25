package com.takalobazar.admin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takalobazar.admin.domain.APIResponse.ReportDetailResponse;
import com.takalobazar.admin.domain.APIResponse.ReportItem;
import com.takalobazar.admin.domain.APIResponse.ReportResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static com.takalobazar.admin.service.Constants.API_URL;

@Service
public class ReportService {

    private final RestTemplate restTemplate;

    public ReportService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ReportResponse getReports(int page, int limit) throws Exception {
        String url = UriComponentsBuilder.fromHttpUrl(API_URL.concat("/reports"))
                .queryParam("page", page)
                .queryParam("limit", limit)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode dataNode = objectMapper.readTree(response).path("data");
        List<ReportItem> reports = Arrays.asList(objectMapper.treeToValue(dataNode.path("reports"), ReportItem[].class));

        ReportResponse reportResponse = new ReportResponse();
        reportResponse.setReports(reports);
        reportResponse.setTotalItems(dataNode.path("totalItems").asInt());
        reportResponse.setTotalPages(dataNode.path("totalPages").asInt());
        reportResponse.setCurrentPage(dataNode.path("currentPage").asInt());

        return reportResponse;
    }

    public ReportDetailResponse getReportDetails(int objectId, String createdAtStart, String createdAtEnd, String reason, Integer page) throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(API_URL.concat("/object/") + objectId + "/reports");
    
        if (createdAtStart != null && !createdAtStart.isEmpty()) {
            uriBuilder.queryParam("created_at_start", createdAtStart);
        }
        if (createdAtEnd != null && !createdAtEnd.isEmpty()) {
            uriBuilder.queryParam("created_at_end", createdAtEnd);
        }
        if (reason != null && !reason.isEmpty()) {
            uriBuilder.queryParam("reason", reason);
        }
        if (page != null) {
            uriBuilder.queryParam("page", page);
        }
    
        String response = restTemplate.getForObject(uriBuilder.toUriString(), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, ReportDetailResponse.class);
    }
    
}
