package com.keenor.resttempalate;

import com.keenor.resttempalate.exception.ApiCodeException;
import com.keenor.resttempalate.pojo.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestClientUtil {

    RestTemplate restTemplate = new RestTemplate();

    public <T> T getForType(String url, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws ApiCodeException {
        T body = restTemplate.exchange(url, HttpMethod.GET, null, responseType, uriVariables).getBody();
        handleApiError(body);
        return body;
    }

    public <T> T getForType(String url, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) {
        return restTemplate.exchange(url, HttpMethod.GET, null, responseType, uriVariables).getBody();
    }

    public <T> T postForType(String url, @Nullable Object request, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws ApiCodeException {
        HttpEntity<?> requestEntity = getHttpEntity(request);
        T body = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables).getBody();
        handleApiError(body);
        return body;
    }

    public <T> T postForType(String url, @Nullable Object request, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) throws ApiCodeException {
        HttpEntity<?> requestEntity = getHttpEntity(request);
        T body = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables).getBody();
        handleApiError(body);
        return body;
    }

    HttpEntity<?> getHttpEntity(@Nullable Object request) {
        HttpEntity<?> requestEntity;
        if (request instanceof HttpEntity) {
            requestEntity = (HttpEntity<?>) request;
        } else if (request != null) {
            requestEntity = new HttpEntity<Object>(request);
        } else {
            requestEntity = HttpEntity.EMPTY;
        }
        return requestEntity;
    }

    <T> void handleApiError(T body) throws ApiCodeException {
        if (body instanceof Result) {
            Result result = (Result) body;
            if (!result.isSuccess()) {
                throw new ApiCodeException(result.getCode(), result.getMsg());
            }
        }
    }
}