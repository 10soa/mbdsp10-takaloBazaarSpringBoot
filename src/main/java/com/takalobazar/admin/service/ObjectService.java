package com.takalobazar.admin.service;

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
public class ObjectService {

    private final RestTemplate restTemplate;

    public ObjectService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object createObject(String name, String description, Integer categoryId, MultipartFile imageFile, Integer userId) throws IOException {
        if (name == null || description == null || categoryId == null || imageFile == null || userId == null) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        String url = Constants.API_URL.concat("/objects");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String contentType = imageFile.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        String base64Image = Base64.getEncoder().encodeToString(imageFile.getBytes());
        String imageBase64 = "data:" + contentType + ";base64," + base64Image;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("description", description);
        requestBody.put("category_id", categoryId);
        requestBody.put("image_file", imageBase64);
        requestBody.put("user_id", userId);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = restTemplate.postForEntity(url, requestEntity, Object.class);
        } catch (Exception e) {
            throw new IOException("Error", e);
        }

        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return responseEntity.getBody();
        } else {
            throw new IOException("Error : " + responseEntity.getStatusCode());
        }
    }

    // Update Object
    public Object updateObject(String id, String name, String description, Integer categoryId, MultipartFile imageFile) throws IOException {
        String url = Constants.API_URL.concat("/objects/"+id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("description", description);
        body.put("category_id", categoryId);
        if (imageFile != null && !imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            String base64Image = Base64.getEncoder().encodeToString(imageFile.getBytes());
            String imageBase64 = "data:" + contentType + ";base64," + base64Image;
            body.put("image_file", imageBase64);
        }
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
        return response.getBody();
    }

    // getOneObject
    public Object getObjectById(String objectId) throws IOException {
        String url = Constants.API_URL.concat("/object/").concat(objectId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Object.class);
        } catch (Exception e) {
            throw new IOException("Error fetching object details", e);
        }
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new IOException("Error: " + responseEntity.getStatusCode());
        }
    }

    // getAllObjects + Filtre
    public Map<String, Object> getObjects(Map<String, String> filters) throws IOException {
        String url = Constants.API_URL.concat("/objects");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("limit", 10);
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
            if (responseBody != null && responseBody.containsKey("data")) {
                Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                return data;
            } else {
                throw new IOException("Invalid response structure");
            }
        } else {
            throw new IOException("Error: " + responseEntity.getStatusCode());
        }
    }
}
