package com.carpooling.backend.controller;

import com.carpooling.backend.model.Ride;
import com.carpooling.backend.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RideController {

    @Autowired
    private RideService service;

    @PostMapping("addRide")
    public Ride addRide(@RequestBody Ride ride){
        return service.saveRide(ride);
    }

    @GetMapping("getRide/{rideId}")
    public Ride getRide(@PathVariable String rideId){
        return service.getRide(rideId);
    }

    @GetMapping("getAllRides/{from}/{to}")
    public List<Ride> getAllRides(@PathVariable String fromLocation, @PathVariable String toLocation){
        
    }
}
