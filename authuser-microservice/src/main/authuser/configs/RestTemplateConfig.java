package com.distancelearning.authuser.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    static final int TIMEOUT_MILLISEC=5000;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        //Do any configuration here
        return builder
                .setConnectTimeout(Duration.ofMillis(TIMEOUT_MILLISEC))
                .setReadTimeout(Duration.ofMillis(TIMEOUT_MILLISEC))
                .build();
    }
}
