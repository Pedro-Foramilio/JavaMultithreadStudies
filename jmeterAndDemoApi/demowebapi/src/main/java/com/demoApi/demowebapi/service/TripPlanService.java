package com.demoApi.demowebapi.service;


import com.demoApi.demowebapi.client.AccomodationServiceClient;
import com.demoApi.demowebapi.client.EventServiceClient;
import com.demoApi.demowebapi.client.FlightReservationServiceClient;
import com.demoApi.demowebapi.client.FlightSearchServiceClient;
import com.demoApi.demowebapi.client.LocalRecommendationServiceClient;
import com.demoApi.demowebapi.client.TransportationServiceClient;
import com.demoApi.demowebapi.client.WeatherServiceClient;
import com.demoApi.demowebapi.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class TripPlanService {
    public static final Logger log = LoggerFactory.getLogger(TripPlanService.class);
    private final AccomodationServiceClient accomodationServiceClient;
    private final EventServiceClient eventServiceClient;
    private final FlightReservationServiceClient flightReservationServiceClient;
    private final FlightSearchServiceClient flightSearchServiceClient;
    private final LocalRecommendationServiceClient localRecommendationServiceClient;
    private final TransportationServiceClient transportationServiceClient;
    private final WeatherServiceClient weatherServiceClient;

    private final ExecutorService executor;

    public TripPlan getTripPlan(String airportCode) {
        Future<List<Event>> events = this.executor.submit(() -> this.eventServiceClient.getEvents(airportCode));
        Future<Weather> weather = this.executor.submit(() -> this.weatherServiceClient.getWeather(airportCode));
        Future<List<Accommodation>> accomodations = this.executor.submit(() -> this.accomodationServiceClient.getAccomodations(airportCode));
        Future<Transportation> transportation = this.executor.submit(() -> this.transportationServiceClient.getTransportation(airportCode));
        Future<LocalRecommendations> recommendations = this.executor.submit(() -> this.localRecommendationServiceClient.getRecomendations(airportCode));

        return new TripPlan(
                airportCode,
                getOrElse(accomodations, List.of()),
                getOrElse(weather, null),
                getOrElse(events, List.of()),
                getOrElse(recommendations, null),
                getOrElse(transportation, null)
        );
    }

    private <T> T getOrElse(Future<T> future, T defaultValue) {
        try {
            return future.get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
