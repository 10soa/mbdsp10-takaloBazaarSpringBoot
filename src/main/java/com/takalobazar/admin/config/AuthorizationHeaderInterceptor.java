package com.takalobazar.admin.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class AuthorizationHeaderInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFzbWl0aEBleGFtcGxlLmNvbSIsImlkIjo1MSwiZmlyc3RfbmFtZSI6IkFsaWNlIiwibGFzdF9uYW1lIjoiQWxpY2UiLCJ1c2VybmFtZSI6ImFzbWl0aCIsInR5cGUiOiJBRE1JTiIsImp0aSI6IjUxLTE3MjIwMjEzNTE1MTAiLCJpYXQiOjE3MjIwMjEzNTEsImV4cCI6MTcyMjE5NDE1MX0.EK4KvDiScP_uiYIeRJVruMXblIsW5_JssGdcvXyqcRg");
        return execution.execute(request, body);
    }
}
