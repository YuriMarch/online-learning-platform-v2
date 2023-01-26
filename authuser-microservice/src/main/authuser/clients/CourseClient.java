package com.distancelearning.authuser.clients;

import com.distancelearning.authuser.dtos.CourseDto;
import com.distancelearning.authuser.dtos.ResponsePageDto;
import com.distancelearning.authuser.services.UtilsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${distancelearning.api.url.course}")
    String REQUEST_URL_COURSE;

//    @Retry(name = "retryInstance", fallbackMethod = "retryFallBack") Do not use much retry because it creates more requests

    @CircuitBreaker(name = "circuitbreakerInstance", fallbackMethod = "circuitBreakerFallBack")
    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable) {
        List<CourseDto> searchResult = null;
        String url = REQUEST_URL_COURSE + utilsService.createUrlGetAllCoursesByUser(userId, pageable);

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);
        System.out.println("--- Start request to Course Microservice ---");
        ResponseEntity<ResponsePageDto<CourseDto>> result = null;
        try {
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType =
                    new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();

            log.debug("Response number of elements: {}", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {}", e);
        }
        log.info("Ending request /courses userId {}", userId);
        return result.getBody();
    }

    public Page<CourseDto> circuitBreakerFallBack (UUID userId, Pageable pageable, Throwable t){
        log.error("Inside circuit breaker circuitBreakerFallBack, cause - {}", t.toString());
        List<CourseDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult);
    }

    public Page<CourseDto> retryFallBack(UUID userId, Pageable pageable, Throwable t){
        log.error("Inside retry retryFallBack, cause - {}", t.toString());
        List<CourseDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult);
    }
}
