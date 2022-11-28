package com.javadeveloper.computershare.service.impl;

import com.javadeveloper.computershare.exceptions.ComputerShareException;
import com.javadeveloper.computershare.service.CompanyDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class CompanyDetailServiceImpl implements CompanyDetailService {

    @Value("${apiPath}")
    private String apiPath;

    @Value("${apikey}")
    private String apikey;

    @Override
    public String weeklyStockPriceByCompanyName(String symbol) {
        // Query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiPath)
                // Add query parameter
                .queryParam("function", "TIME_SERIES_WEEKLY")
                .queryParam("symbol", symbol)
                .queryParam("apikey", apikey);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return sendRequest(apiPath, headers, builder);

    }

    private String sendRequest(final String apiPath, MultiValueMap<String, String> headersMap, UriComponentsBuilder builder) {

        log.info("Sending request: {} {} {}", apiPath, headersMap, builder);

        HttpHeaders headers = new HttpHeaders();
        headers.addAll(headersMap);
        HttpEntity<Object> request = new HttpEntity<>(headers);

        final RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(builder.build().toUri(), HttpMethod.GET,
                        request, String.class);
        log.info("Response: {} {}", responseEntity.getStatusCode(), responseEntity.getBody());
        if (responseEntity.getStatusCode() == HttpStatus.OK && Objects.nonNull(responseEntity.getBody())) {
            return responseEntity.getBody();
        }
        throw new ComputerShareException("Error in getting third party response.");
    }
}
