package com.exercise.proxyserver.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Eddy Rodriguez
 */
public class MissingURLException extends ProxyServerException {

    public MissingURLException() {
        super("001", HttpStatus.BAD_REQUEST, "URL is missing on the request");
    }
}
