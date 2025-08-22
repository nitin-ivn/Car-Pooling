package com.carpooling.backend.scheduler;

import com.carpooling.backend.service.RideServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;


@Component
public class RideScheduler {

    private final RideServiceImpl rideService;

    public RideScheduler(RideServiceImpl service) {
        this.rideService = service;
    }

    //This runs every fixed time and updated the rides whose ride date and time are over
    @Scheduled(fixedRate = 60000)
    public void updateActiveRides(){
        System.out.println(LocalTime.now());
        rideService.updateActiveRides();
    }
}
