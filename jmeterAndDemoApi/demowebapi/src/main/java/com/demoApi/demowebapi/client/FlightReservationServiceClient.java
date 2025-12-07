package com.demoApi.demowebapi.client;

import com.demoApi.demowebapi.dto.FlightReservationRequest;
import com.demoApi.demowebapi.dto.FlightReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class FlightReservationServiceClient {
    private final RestClient client;

    public FlightReservationResponse reserve(FlightReservationRequest request) {
        return this.client.post()
                .body(request)
                .retrieve()
                .body(FlightReservationResponse.class);
    }
}
