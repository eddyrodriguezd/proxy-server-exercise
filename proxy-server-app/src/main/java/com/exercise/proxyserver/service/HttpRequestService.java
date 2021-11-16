package com.exercise.proxyserver.service;

import com.exercise.proxyserver.dto.HttpResponseDto;
import com.exercise.proxyserver.exception.ProxiedServerErrorException;

/**
 * @author Eddy Rodriguez
 */
public interface HttpRequestService {

    /**
     * Sends the request to the server and store its response
     * @param url - URL to send the request to
     * @return HttpResponseDto - Response (headers and body) from server
     * @throws ProxiedServerErrorException
     */
    HttpResponseDto getHttpResponse(String url) throws ProxiedServerErrorException;
}
