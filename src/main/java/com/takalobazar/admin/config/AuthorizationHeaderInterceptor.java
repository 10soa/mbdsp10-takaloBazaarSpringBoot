package com.takalobazar.admin.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthorizationHeaderInterceptor implements ClientHttpRequestInterceptor {

    private final HttpSession httpSession;

    public AuthorizationHeaderInterceptor(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token = (String) httpSession.getAttribute("AUTH_TOKEN");
        if (token != null && !token.isEmpty()) {
            request.getHeaders().set("Authorization", "Bearer " + token);
        }

        ClientHttpResponse response = execution.execute(request, body);

        if (response.getStatusCode().value() == 401) {
            throw new UnauthorizedException("User needs to re-authenticate.");
        }

        return response;
    }
}
