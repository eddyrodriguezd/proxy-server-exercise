package com.exercise.proxyserver.client;

import com.exercise.proxyserver.exception.ProxiedServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Class responsible for handling Rest Template response errors
 *
 * @author Eddy Rodriguez
 **/
public class ErrorHandler {

    /**
     * Error Handler for GET requests to chosen URL
     **/
    public static class HttpRequestClientErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return !clientHttpResponse.getStatusCode().equals(HttpStatus.OK);
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            throw new ProxiedServerErrorException();
        }
    }
}
