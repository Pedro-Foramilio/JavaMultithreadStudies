package com.demoApi.demowebapi.client;

import com.demoApi.demowebapi.dto.LocalRecommendations;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class LocalRecommendationServiceClient {
    private final RestClient client;

    public LocalRecommendations getRecomendations(String airportCode) {
        return this.client.get()
                .uri("{airportCode}", airportCode)
                .retrieve()
                .body(LocalRecommendations.class);
    }
}
