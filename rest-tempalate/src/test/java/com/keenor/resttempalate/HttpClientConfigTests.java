package com.keenor.resttempalate;

import com.keenor.resttempalate.config.HttpClientConfig;
import com.keenor.resttempalate.config.RestTemplateHttpClientConfig;
import com.keenor.resttempalate.pojo.BookWrapVo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

/**
 * junit5 新写法
 * https://www.javacodemonk.com/migrating-spring-boot-tests-from-junit-4-to-junit-5-00aa2839
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RestTemplateHttpClientConfig.class, HttpClientConfig.class})
public class HttpClientConfigTests {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void testGet() {
        // 不带参数
        String url = "http://localhost:8080/book/" + "/list";
        BookWrapVo wrapVo = restTemplate.getForObject(url, BookWrapVo.class);
        System.out.println("GET 1-1 => " + wrapVo);
    }


}
