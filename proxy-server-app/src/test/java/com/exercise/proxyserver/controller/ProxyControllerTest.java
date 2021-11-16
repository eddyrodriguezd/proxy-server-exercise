package com.exercise.proxyserver.controller;

import com.exercise.proxyserver.dto.HttpResponseDto;
import com.exercise.proxyserver.exception.InvalidURLException;
import com.exercise.proxyserver.exception.ProxiedServerErrorException;
import com.exercise.proxyserver.service.HttpRequestService;
import com.exercise.proxyserver.service.URLValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Eddy Rodriguez
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProxyController.class)
@ContextConfiguration(classes = ProxyControllerTest.TestConfiguration.class)
class ProxyControllerTest {

    static class TestConfiguration {
        @Bean
        public ProxyController controller(HttpRequestService httpRequestService,
                                          URLValidatorService urlService) {
            return new ProxyController(httpRequestService, urlService);
        }

        @Bean
        public ControllerExceptionHandler handler() {
            return new ControllerExceptionHandler();
        }
    }

    @Autowired
    private ProxyController controller;

    @Autowired
    private ControllerExceptionHandler handler;

    @MockBean
    private HttpRequestService httpRequestService;

    @MockBean
    private URLValidatorService urlService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws IOException {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(handler).build();
    }

    @Test
    void forward_200() throws Exception {
        // Preparing data
        URL url = new URL("https://www.google.com");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "text/html;charset=utf-8");
        httpHeaders.add("X-Content-Type-Options", "nosniff");
        HttpResponseDto httpResponseDto = HttpResponseDto.builder().headers(httpHeaders).body(null).build();

        // Mocks & Stubs configuration
        when(urlService.getValidUrl("www.google.com")).thenReturn(url);
        when(httpRequestService.getHttpResponse("https://www.google.com")).thenReturn(httpResponseDto);

        // Business logic execution
        mockMvc.perform(get("/")
                        .queryParam("url", "www.google.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        //Validate responses
        verify(urlService, times(1)).getValidUrl("www.google.com");
        verify(httpRequestService, times(1)).getHttpResponse("https://www.google.com");
    }

    @Test
    void forward_400_MissingURLParam() throws Exception {
        // Preparing data

        // Mocks & Stubs configuration

        // Business logic execution
        mockMvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //Validate responses
        verify(urlService, times(0)).getValidUrl(anyString());
        verify(httpRequestService, times(0)).getHttpResponse(anyString());
    }

    @Test
    void forward_400_InvalidURL() throws Exception {
        // Preparing data

        // Mocks & Stubs configuration
        doThrow(new InvalidURLException()).when(urlService).getValidUrl("www.google.com");

        // Business logic execution
        mockMvc.perform(get("/")
                        .queryParam("url", "www.google.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //Validate responses
        verify(urlService, times(1)).getValidUrl("www.google.com");
        verify(httpRequestService, times(0)).getHttpResponse(anyString());
    }

    @Test
    void forward_409_ProxiedServerErrorResponse() throws Exception {
        // Preparing data
        URL url = new URL("https://www.google.com");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "text/html;charset=utf-8");
        httpHeaders.add("X-Content-Type-Options", "nosniff");
        HttpResponseDto httpResponseDto = HttpResponseDto.builder().headers(httpHeaders).body(null).build();

        // Mocks & Stubs configuration
        when(urlService.getValidUrl("www.google.com")).thenReturn(url);
        doThrow(new ProxiedServerErrorException()).when(httpRequestService).getHttpResponse("https://www.google.com");

        // Business logic execution
        mockMvc.perform(get("/")
                        .queryParam("url", "www.google.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        //Validate responses
        verify(urlService, times(1)).getValidUrl("www.google.com");
        verify(httpRequestService, times(1)).getHttpResponse("https://www.google.com");
    }


}