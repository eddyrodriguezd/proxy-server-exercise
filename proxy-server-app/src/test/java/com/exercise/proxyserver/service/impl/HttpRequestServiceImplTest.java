package com.exercise.proxyserver.service.impl;

import com.exercise.proxyserver.client.HttpRequestClient;
import com.exercise.proxyserver.dto.HttpResponseDto;
import com.exercise.proxyserver.service.HttpRequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Eddy Rodriguez
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HttpRequestServiceImplTest.TestConfiguration.class)
class HttpRequestServiceImplTest {

    static class TestConfiguration {
        @Bean
        public HttpRequestService httpRequestService(HttpRequestClient httpRequestClient) {
            return new HttpRequestServiceImpl(httpRequestClient);
        }
    }

    @Autowired
    private HttpRequestService httpRequestService;

    @MockBean
    private HttpRequestClient httpRequestClient;

    @Test
    void getHttpResponse() {
        // Preparing data
        HttpResponseDto httpResponseDto = HttpResponseDto.builder().build();

        // Mocks & Stubs configuration
        when(httpRequestClient.performRequest("https://www.google.com")).thenReturn(httpResponseDto);

        // Validating mocks behaviour
        assertDoesNotThrow(() -> httpRequestService.getHttpResponse("https://www.google.com"));
    }
}