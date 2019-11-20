package com.keenor.resttempalate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }


}