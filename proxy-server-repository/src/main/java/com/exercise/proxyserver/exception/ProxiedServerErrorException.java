package com.exercise.proxyserver.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Eddy Rodriguez
 */
public class ProxiedServerErrorException extends ProxyServerException {

    public ProxiedServerErrorException() {
        super("003", HttpStatus.CONFLICT, "Proxied server doesn't exist or is currently unavailable");
    }
}
