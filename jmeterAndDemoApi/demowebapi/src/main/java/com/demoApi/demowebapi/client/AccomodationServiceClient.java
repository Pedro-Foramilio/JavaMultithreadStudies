package com.demoApi.demowebapi.client;

import com.demoApi.demowebapi.dto.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class AccomodationServiceClient {

    private final RestClient client;

    public List<Accommodation> getAccomodations(String airportCode) {
        return this.client.get()
                .uri("{airportCode}", airportCode)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Accommodation>>() {
                });
    }
}
