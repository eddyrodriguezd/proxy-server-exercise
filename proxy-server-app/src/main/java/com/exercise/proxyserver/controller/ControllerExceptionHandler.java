package com.exercise.proxyserver.controller;

import com.exercise.proxyserver.exception.MissingURLException;
import com.exercise.proxyserver.exception.ProxyServerException;
import com.exercise.proxyserver.exception.InvalidURLException;
import com.exercise.proxyserver.exception.ProxiedServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Eddy Rodriguez
 *
 * Class that handles exceptions from Controller
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("==========START REQUEST==========");
        MissingURLException exception = new MissingURLException();
        log.info(exception.getBody().getMessage());
        log.info("==========END REQUEST==========\n");
        return handleExceptionInternal(exception, exception.getBody(), new HttpHeaders(), exception.getBody().getStatus(), request);
    }

    @ExceptionHandler(value = {InvalidURLException.class, ProxiedServerErrorException.class})
    protected ResponseEntity<Object> handleInvalidURL(RuntimeException exception, WebRequest request) {
        ProxyServerException ex = (ProxyServerException) exception;
        log.info(ex.getBody().getMessage());
        log.info("==========END REQUEST==========\n");
        return handleExceptionInternal(ex, ex.getBody(), new HttpHeaders(), ex.getBody().getStatus(), request);
    }
}