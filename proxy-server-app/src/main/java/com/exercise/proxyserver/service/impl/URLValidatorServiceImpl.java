package com.exercise.proxyserver.service.impl;

import com.exercise.proxyserver.exception.InvalidURLException;
import com.exercise.proxyserver.service.URLValidatorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Eddy Rodriguez
 */
@Service
public class URLValidatorServiceImpl implements URLValidatorService {

    private final String[] VALID_PROTOCOLS = new String[]{"https", "http"};

    @Override
    public URL getValidUrl(String urlStr) {
        try {
            urlStr = addSchemeIfNecessary(urlStr.toLowerCase());
            return new URL(urlStr);
        } catch (MalformedURLException | IllegalArgumentException e) {
            throw new InvalidURLException();
        }
    }

    private String addSchemeIfNecessary(String url) {
        if(!StringUtils.startsWithAny(url, VALID_PROTOCOLS)) {
            url = VALID_PROTOCOLS[0] + "://" + url;
        }
        return url;
    }

}
