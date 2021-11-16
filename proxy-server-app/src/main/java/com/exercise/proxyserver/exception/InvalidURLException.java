package com.exercise.proxyserver.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Eddy Rodriguez
 */
public class InvalidURLException extends ProxyServerException {

    public InvalidURLException() {
        super("002", HttpStatus.BAD_REQUEST, "Given URL couldn't be parsed to a valid URL");
    }
}
