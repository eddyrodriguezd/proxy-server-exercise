package com.exercise.proxyserver.client;

import com.exercise.proxyserver.dto.HttpResponseDto;

/**
 * @author Eddy Rodriguez
 */
public interface HttpRequestClient {

    /**
     * Sends the request to the server and store its response
     * @param url - URL to send the request to
     * @return HttpResponseDto - Response (headers and body) from server
     */
    HttpResponseDto performRequest(String url);
}
