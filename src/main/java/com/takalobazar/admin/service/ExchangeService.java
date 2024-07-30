package com.takalobazar.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ExchangeService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> getExchanges(Map<String, String> filters) throws IOException {
        String url = Constants.API_URL.concat("/exchanges");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("limit", 5);
        for (Map.Entry<String, String> filter : filters.entrySet()) {
            builder.queryParam(filter.getKey(), filter.getValue());
        }
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> responseEntity;
        try {
            responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new IOException("Error fetching objects", e);
        }
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = responseEntity.getBody();
            if (responseBody != null) {
                List<Object> exchanges = (List<Object>) responseBody.get("exchanges");
                Integer totalPages = (Integer) responseBody.get("totalPages");
                String currentPageString = (String) responseBody.get("currentPage");
                Map<String, Object> result = new HashMap<>();
                result.put("exchanges", exchanges);
                result.put("totalPages", totalPages);
                result.put("currentPage", currentPageString);
                return result;
            } else {
                throw new IOException("Invalid response structure");
            }
        } else {
            throw new IOException("Error: " + responseEntity.getStatusCode());
        }
    }

    // fiche des Echanges
    public Map<String, Object> getExchangeById(Long exchangeId) throws IOException {
        String url = Constants.API_URL.concat("/exchange/").concat(exchangeId.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new IOException("Error fetching exchange", e);
        }

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new IOException("Error: " + responseEntity.getStatusCode());
        }
    }

}
