package com.exercise.proxyserver.service;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Eddy Rodriguez
 */
public interface URLValidatorService {

    /**
     * Check if the String received is a valid URL
     * @param url - URL to check
     * @return URL - Valid URL
     * @throws MalformedURLException
     */
    URL getValidUrl(String url) throws MalformedURLException;
}
