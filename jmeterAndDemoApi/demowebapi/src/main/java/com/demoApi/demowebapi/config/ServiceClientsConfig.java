package com.demoApi.demowebapi.config;

import com.demoApi.demowebapi.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.util.concurrent.Executors;

@Configuration
public class ServiceClientsConfig {
    public static final Logger log = LoggerFactory.getLogger(ServiceClientsConfig.class);

    @Value("${spring.threads.virtual.enabled}")
    private boolean isVirtualEnabled;

    @Bean
    public AccomodationServiceClient accomodationServiceClient(@Value("${accommodation.service.url}") String baseUrl) {
        return new AccomodationServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public EventServiceClient eventServiceClient(@Value("${event.service.url}") String baseUrl) {
        return new EventServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public WeatherServiceClient weatherServiceClient(@Value("${weather.service.url}") String baseUrl) {
        return new WeatherServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public TransportationServiceClient transportationServiceClient(@Value("${transportation.service.url}") String baseUrl) {
        return new TransportationServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public LocalRecommendationServiceClient localRecommendationServiceClient(@Value("${local-recommendation.service.url}") String baseUrl) {
        return new LocalRecommendationServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public FlightSearchServiceClient flightSearchServiceClient(@Value("${flight-search.service.url}") String baseUrl) {
        return new FlightSearchServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public FlightReservationServiceClient flightReservationServiceClient(@Value("${flight-reservation.service.url}") String baseUrl) {
        return new FlightReservationServiceClient(buildRestClient(baseUrl));
    }

    private RestClient buildRestClient(String baseUrl) {
        log.info("Creating RestClient for URL: {}", baseUrl);

        RestClient.Builder builder = RestClient.builder().baseUrl(baseUrl);

        if (isVirtualEnabled) {
            builder = builder.requestFactory(new JdkClientHttpRequestFactory(
                    HttpClient.newBuilder()
                            .executor(Executors.newVirtualThreadPerTaskExecutor())
                            .build()
            ));
        }

        return builder.build();
    }
}
