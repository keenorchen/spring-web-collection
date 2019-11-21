package com.keenor.resttempalate.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);

        // Add optional additional headers
        response.getHeaders().add("headerName", "VALUE");

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (log.isDebugEnabled()) {
            String buffer = "\n" +
                    "=========================== REQUEST BEGIN ===================================\n" +
                    "URI         : " + request.getURI() + "\n" +
                    "Method      : " + request.getMethod() + "\n" +
                    "Headers     : " + request.getHeaders() + "\n" +
                    "Request body: " + new String(body, StandardCharsets.UTF_8) + "\n" +
                    "+++++++++++++++++++++++++++ REQUEST END =====================================";
            log.debug(buffer);
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (log.isDebugEnabled()) {
            String buffer = "\n" +
                    "=========================== RESPONSE BEGIN ==================================\n" +
                    "Http Status  : " + response.getStatusCode() + "\n" +
                    "Headers      : " + response.getHeaders() + "\n" +
                    "Response body: " + StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()) + "\n" +
                    "^^^^^^^^^^^^^^^^^^^^^^^^^^^ RESPONSE END ====================================";
            log.debug(buffer);
        }

    }


}
