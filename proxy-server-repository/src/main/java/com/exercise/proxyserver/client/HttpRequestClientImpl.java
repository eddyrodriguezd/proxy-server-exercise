package com.exercise.proxyserver.client;

import com.exercise.proxyserver.dto.HttpResponseDto;
import com.exercise.proxyserver.exception.ProxiedServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Eddy Rodriguez
 */
@Slf4j
@Repository
public class HttpRequestClientImpl implements HttpRequestClient {

    @Override
    public HttpResponseDto performRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ErrorHandler.HttpRequestClientErrorHandler());

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    String.class);
        } catch (IllegalArgumentException | RestClientException e) {
            throw new ProxiedServerErrorException();
        }

        HttpResponseDto httpResponseDto = HttpResponseDto.builder().body(response.getBody()).headers(response.getHeaders()).build();
        log.info("Requested (proxied) server headers: " + httpResponseDto.getHeaders());

        return httpResponseDto;
    }
}
