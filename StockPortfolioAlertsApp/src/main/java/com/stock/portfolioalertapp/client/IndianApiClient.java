package com.stock.portfolioalertapp.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class IndianApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${market.api.base-url:https://stock.indianapi.in}")
    private String baseUrl;

    @Value("${market.api.key:}")
    private String apiKey;

    public IndianApiClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public JsonNode getQuoteNode(String symbol) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Market API key is missing. Set INDIAN_API_KEY.");
        }

        String url = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path("/stock")
                .queryParam("name", symbol)
                .build()
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException(
                    "Market API returned HTTP " + response.getStatusCode().value());
        }

        try {
            JsonNode node = objectMapper.readTree(
                    response.getBody() == null ? "{}" : response.getBody());

            if (node.has("error")) {
                throw new IllegalStateException(node.path("error").asText());
            }

            return node;
        } catch (Exception e) {
            if (e instanceof IllegalStateException) {
                throw (IllegalStateException) e;
            }
            throw new IllegalStateException(
                    "Invalid response from market API", e);
        }
    }

    public String getQuote(String symbol) {
        return getQuoteNode(symbol).toString();
    }

    public String getTrendingStocks() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Market API key is missing. Set INDIAN_API_KEY.");
        }

        String url = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path("/trending")
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException(
                    "Market API returned HTTP " + response.getStatusCode().value());
        }

        return response.getBody();
    }
}
