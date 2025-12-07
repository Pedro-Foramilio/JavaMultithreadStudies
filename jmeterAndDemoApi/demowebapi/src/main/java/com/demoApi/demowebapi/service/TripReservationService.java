package com.demoApi.demowebapi.service;

import com.demoApi.demowebapi.client.FlightReservationServiceClient;
import com.demoApi.demowebapi.client.FlightSearchServiceClient;
import com.demoApi.demowebapi.dto.Flight;
import com.demoApi.demowebapi.dto.FlightReservationRequest;
import com.demoApi.demowebapi.dto.FlightReservationResponse;
import com.demoApi.demowebapi.dto.TripReservationRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripReservationService {

    public static final Logger log = LoggerFactory.getLogger(TripReservationService.class);
    private final FlightSearchServiceClient searchServiceClient;
    private final FlightReservationServiceClient reservationServiceClient;

    public FlightReservationResponse reserve(TripReservationRequest request) {
        List<Flight> flights = this.searchServiceClient.getFlights(request.departure(), request.arrival());

        Optional<Flight> bestDealopt = flights.stream().min(
                Comparator.comparingInt(Flight::price)
        );

        Flight bestDeal = bestDealopt.orElseThrow(() -> {
            log.error("No flights found for the given route: {} to {}", request.departure(), request.arrival());
            return new IllegalStateException("No flights available for the selected route.");
        });

        FlightReservationRequest reservationRequest = new FlightReservationRequest(
                request.departure(),
                request.arrival(),
                bestDeal.flightNumber(),
                request.date()
        );

        return this.reservationServiceClient.reserve(reservationRequest);
    }

}
