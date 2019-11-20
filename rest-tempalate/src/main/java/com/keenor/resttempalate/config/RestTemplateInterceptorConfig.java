package com.keenor.resttempalate.config;

import com.keenor.resttempalate.interceptor.RequestResponseLoggingInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class RestTemplateInterceptorConfig {

    /**
     * 除了配置ClientHttpRequestInterceptor之外，还配置了RestTemplate的ClientHttpRequestFactory，
     * 这是因为RestTemplate默认使用的是SimpleClientHttpRequestFactory，它的Body流只能读取一次，
     * 如果我们记录日志的时候，把Body读取打印出来，那么接下去在去读Body流的时候读不到就会报错，
     * 所以如果需要在日志中记录应答的Body，就可以使用BufferingClientHttpRequestFactory，
     * 它会通过用缓冲流存储请求体的方式，让我们可以多次重复读取Body流。
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        //        restTemplate.setMessageConverters(Collections.singletonList(mappingJacksonHttpMessageConverter()));
        restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

        return restTemplate;
    }


}