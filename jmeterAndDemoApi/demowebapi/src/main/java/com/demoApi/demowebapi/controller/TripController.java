package com.demoApi.demowebapi.controller;

import com.demoApi.demowebapi.dto.FlightReservationResponse;
import com.demoApi.demowebapi.dto.TripPlan;
import com.demoApi.demowebapi.dto.TripReservationRequest;
import com.demoApi.demowebapi.service.TripPlanService;
import com.demoApi.demowebapi.service.TripReservationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trip")
@RequiredArgsConstructor
public class TripController {

    public static final Logger log = LoggerFactory.getLogger(TripController.class);

    private final TripPlanService planService;
    private final TripReservationService tripReservationService;

    @GetMapping("{airportCode}")
    public TripPlan planTrip(@PathVariable String airportCode) {
        log.info("airport code: {}, is virtual: {}", airportCode, Thread.currentThread().isVirtual());
        return this.planService.getTripPlan(airportCode);
    }

    @PostMapping("reserve")
    public FlightReservationResponse reserveFlight(@RequestBody TripReservationRequest request) {
        return this.tripReservationService.reserve(request);
    }
}
