package com.demoApi.demowebapi.client;

import com.demoApi.demowebapi.dto.Transportation;
import com.demoApi.demowebapi.dto.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class WeatherServiceClient {
    private final RestClient client;

    public Weather getWeather(String airportCode) {
        return this.client.get()
                .uri("{airportCode}", airportCode)
                .retrieve()
                .body(Weather.class);
    }
}
