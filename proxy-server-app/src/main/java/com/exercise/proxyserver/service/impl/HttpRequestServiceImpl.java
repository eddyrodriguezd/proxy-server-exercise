package com.exercise.proxyserver.service.impl;

import com.exercise.proxyserver.client.HttpRequestClient;
import com.exercise.proxyserver.dto.HttpResponseDto;
import com.exercise.proxyserver.exception.ProxiedServerErrorException;
import com.exercise.proxyserver.service.HttpRequestService;
import org.springframework.stereotype.Service;

/**
 * @author Eddy Rodriguez
 */
@Service
public class HttpRequestServiceImpl implements HttpRequestService {

    private final HttpRequestClient httpRequestClient;

    public HttpRequestServiceImpl(HttpRequestClient httpRequestClient) {
        this.httpRequestClient = httpRequestClient;
    }

    @Override
    public HttpResponseDto getHttpResponse(String url) throws ProxiedServerErrorException {
        return httpRequestClient.performRequest(url);
    }
}
