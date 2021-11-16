package com.exercise.proxyserver.service.impl;

import com.exercise.proxyserver.exception.InvalidURLException;
import com.exercise.proxyserver.service.URLValidatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Eddy Rodriguez
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = URLValidatorServiceImplTest.TestConfiguration.class)
class URLValidatorServiceImplTest {

    static class TestConfiguration {
        @Bean
        public URLValidatorService urlValidatorService() {
            return new URLValidatorServiceImpl();
        }
    }

    @Autowired
    private URLValidatorService urlValidatorService;

    @Test
    void getValidUrl() throws MalformedURLException {
        assertEquals(urlValidatorService.getValidUrl("www.google.com").toString(), "https://www.google.com");
        assertEquals(urlValidatorService.getValidUrl("http://www.google.com").toString(), "http://www.google.com");
        assertEquals(urlValidatorService.getValidUrl("https://www.google.com").toString(), "https://www.google.com");
        assertThrows(InvalidURLException.class, () -> {urlValidatorService.getValidUrl("http//www.google.com");});
    }
}