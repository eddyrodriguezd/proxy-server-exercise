package com.exercise.proxyserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

/**
 * @author Eddy Rodriguez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponseDto {

    private HttpHeaders headers;
    private String body;
}
