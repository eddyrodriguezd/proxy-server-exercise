package com.exercise.proxyserver.client;

import com.exercise.proxyserver.exception.ProxiedServerErrorException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.Headers;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Eddy Rodriguez
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HttpRequestClientImplTest.TestConfiguration.class)
class HttpRequestClientImplTest {

    static class TestConfiguration {
        @Bean
        public HttpRequestClient httpRequestClient() {
            return new HttpRequestClientImpl();
        }
    }

    @Autowired
    private HttpRequestClient httpRequestClient;

    private static ClientAndServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = ClientAndServer.startClientAndServer(1080);
    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
        while(!mockServer.hasStopped(3, 10, TimeUnit.MILLISECONDS));
    }

    @Test
    void performRequest_200() {
        mockServer.when(HttpRequest.request()
                .withPath("/")
                .withMethod("GET")
                .withHeaders(new ArrayList<>())
        ).respond(HttpResponse.response()
                .withStatusCode(200)
                .withContentType(MediaType.TEXT_HTML)
                .withBody("---response body---")
                .withHeader("Content-Type", "text/html;charset=utf-8")
                .withHeader("X-Content-Type-Options", "nosniff")
        );

        Assertions.assertDoesNotThrow(() -> httpRequestClient.performRequest("http://localhost:1080/"));
    }

    @Test
    void performRequest_500() {
        mockServer.when(HttpRequest.request()
                .withPath("/")
                .withMethod("GET")
                .withHeaders(new ArrayList<>())
        ).respond(HttpResponse.response()
                .withStatusCode(500)
                .withContentType(MediaType.TEXT_HTML)
        );

        Assertions.assertThrows(ProxiedServerErrorException.class, () -> httpRequestClient.performRequest("http://localhost:1080/"));
    }
}