package com.exercise.proxyserver.controller;

import com.exercise.proxyserver.service.HttpRequestService;
import com.exercise.proxyserver.service.URLValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

/**
 * @author Eddy Rodriguez
 *
 * Controller that receives HTTP requests to forward
 */
@RestController
@Slf4j
public class ProxyController {

    private final HttpRequestService httpRequestService;
    private final URLValidatorService urlService;

    public ProxyController(HttpRequestService httpRequestService, URLValidatorService urlService) {
        this.httpRequestService = httpRequestService;
        this.urlService = urlService;
    }

    @GetMapping("/")
    public ResponseEntity<Void> forward(@RequestParam String url) throws MalformedURLException, URISyntaxException {

        log.info("==========START REQUEST==========");
        URL validURL = urlService.getValidUrl(url);

        log.info("Received request to server URL <{}> at <{}>", validURL, new Date());
        httpRequestService.getHttpResponse(validURL.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(validURL.toURI());

        log.info("==========END REQUEST==========\n");
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
