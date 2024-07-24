package com.takalobazar.admin.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class AuthorizationHeaderInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFzbWl0aEBleGFtcGxlLmNvbSIsImlkIjo1MSwiZmlyc3RfbmFtZSI6IkFsaWNlIiwibGFzdF9uYW1lIjoiQWxpY2UiLCJ1c2VybmFtZSI6ImFzbWl0aCIsInR5cGUiOiJBRE1JTiIsImp0aSI6IjUxLTE3MjE4NTAwOTIzNjEiLCJpYXQiOjE3MjE4NTAwOTIsImV4cCI6MTcyMjAyMjg5Mn0.QkmoXfNpNTa3eOYF_IDOenD_KMEUyjSHdFCGy0_e1kw");
        return execution.execute(request, body);
    }
}
