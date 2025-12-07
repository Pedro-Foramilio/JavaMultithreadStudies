package com.demoApi.demowebapi.client;

import com.demoApi.demowebapi.dto.LocalRecommendations;
import com.demoApi.demowebapi.dto.Transportation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class TransportationServiceClient {
    private final RestClient client;

    public Transportation getTransportation(String airportCode) {
        return this.client.get()
                .uri("{airportCode}", airportCode)
                .retrieve()
                .body(Transportation.class);
    }
}
