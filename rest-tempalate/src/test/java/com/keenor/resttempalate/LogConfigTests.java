package com.keenor.resttempalate;

import com.keenor.resttempalate.config.RestTemplateInterceptorConfig;
import com.keenor.resttempalate.pojo.Result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * junit5 新写法
 * https://www.javacodemonk.com/migrating-spring-boot-tests-from-junit-4-to-junit-5-00aa2839
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RestTemplateInterceptorConfig.class})
public class LogConfigTests {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void testGet() {
        // 不带参数
        String url3 = "http://localhost:8080/book/" + "/detail?id={id}";
        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);
        Result result = restTemplate.getForObject(url3, Result.class, params);
        System.out.println("GET 3-1 => " + result);
    }

    @Test
    void testPostJson() {
        String url = "http://localhost:8080/book/" + "/addByJson";
        String title = "丘吉尔回忆录";
        String author = "丘吉尔";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("author", author);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);

        Result result = restTemplate.postForObject(url, request, Result.class);
        System.out.println("POST 4-1 => " + result);

    }


}
