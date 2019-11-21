package com.keenor.resttempalate;

import com.keenor.resttempalate.config.RestTemplateConfig;
import com.keenor.resttempalate.exception.ApiCodeException;
import com.keenor.resttempalate.interceptor.UserAgentInterceptor;
import com.keenor.resttempalate.pojo.BookVo;
import com.keenor.resttempalate.pojo.BookWrapVo;
import com.keenor.resttempalate.pojo.Result;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestTemplateConfig.class)
class BasicDemoTests {

    public static final String URL = "http://localhost:8080/book/";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestClientUtil restClientUtil;

    @Test
    void testGet() throws ApiCodeException {
        // 不带参数
        String url = URL + "/list";
        BookWrapVo wrapVo = restTemplate.getForObject(url, BookWrapVo.class);
        System.out.println("GET 1-1 => " + wrapVo);

        List<BookVo> result2 = null;
        try {
            result2 = restClientUtil.getForType(url, new ParameterizedTypeReference<Result<List<BookVo>>>() {}).getDetail();
        } catch (RestClientException | ApiCodeException e) {
            e.printStackTrace();
        }
        System.out.println("GET 1-2 => " + result2);

        // 传参替换
        // 模板中使用 {?} 来代表坑位，根据实际的传参顺序来填充
        String url2 = URL + "/detail?id={?}";
        Result result = restTemplate.getForObject(url2, Result.class, 1);
        System.out.println("GET 2-1 => " + result);

        Result<BookVo> result1 = restClientUtil.getForType(url2, new ParameterizedTypeReference<Result<BookVo>>() {}, 1);
        System.out.println("GET 2-2 => " + result1);

        // map传参
        // 模板中使用 {xx}, 而这个xx，对应的就是map中的key
        String url3 = URL + "/detail?id={id}";
        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);
        result = restTemplate.getForObject(url3, Result.class, params);
        System.out.println("GET 3-1 => " + result);

        result = restClientUtil.getForType(url3, new ParameterizedTypeReference<Result<BookVo>>() {}, params);
        System.out.println("GET 3-2 => " + result);
    }


    @Test
    void testGetForEntity() {
        String url = URL + "/detail?id={?}";
        ResponseEntity<Result> res = restTemplate.getForEntity(url, Result.class, 1);
        System.out.println(res);
    }


    @Test
    void testPostForm() throws ApiCodeException {
        String url = URL + "/addByForm";
        String title = "明朝那些事";
        String author = "当年明月";
        String content = "皇权vs相权vs宦权";

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("title", title);
        request.add("author", author);

        // 表单传参
        Result result = restTemplate.postForObject(url, request, Result.class);
        System.out.println("POST 1-1 => " + result);

        Result<Long> result1 = restClientUtil.postForType(url, request, new ParameterizedTypeReference<Result<Long>>() {});
        System.out.println("POST 1-2 => " + result1);

        // 结合表单参数和uri参数的方式
        request.clear();
        request.add("title", title + 2);
        result = restTemplate.postForObject(url + "?author={?}", request, Result.class, author + 2);
        System.out.println("POST 2-1 => " + result);

        // 结合表单参数和uri参数的方式，request 和 params 互为补充，冲突时累加
        Map<String, String> params = new HashMap<>();
        params.put("title", title + 3);
        result = restTemplate.postForObject(url + "?title={title}", request, Result.class, params);
        System.out.println("POST 3-1 => " + result);

        result = restClientUtil.postForType(url + "?title={title}", request, new ParameterizedTypeReference<Result<Long>>() {}, params);
        System.out.println("POST 3-2 => " + result);

    }

    @Test
    void testPostJson() throws ApiCodeException {
        String url = URL + "/addByJson";
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

        Result<Long> result1 = restClientUtil.postForType(url, request, new ParameterizedTypeReference<Result<Long>>() {});
        System.out.println("POST 4-2 => " + result1);
    }

    @Test
    public void testGetHeader() {
        String url = URL + "/agent?name=哈哈~";
        RestTemplate restTemplate = new RestTemplate();
        // 注释后会抛出 HttpClientErrorException
        restTemplate.setInterceptors(Collections.singletonList(new UserAgentInterceptor()));
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println("GET HEADER => " + response.getStatusCode() + " | " + response.getBody());
    }

    @Test
    public void testPostHeader() {
        String url = URL + "/agent";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "O(∩_∩)O哈哈~");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        System.out.println("POST HEADER => " + response.getStatusCode() + " | " + response.getBody());
    }

    @Test
    public void testPostFile() {
        String url = URL + "/upload";
        ClassPathResource resource = new ClassPathResource("static/test.txt");
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", resource);
        params.add("fileName", "test.txt");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, params, String.class);
        System.out.println("POST 5-1 => " + response);
    }

    @Test
    void testApiError() {
        String url = URL + "/apiError";
        Result<BookVo> result = null;
        try {
            result = restClientUtil.getForType(url, new ParameterizedTypeReference<Result<BookVo>>() {});
        } catch (ApiCodeException e) {
            e.printStackTrace();
            System.out.println("=> " + e.getMessage());
        }
        System.out.println("GET 6-1 => " + result);
    }


}
