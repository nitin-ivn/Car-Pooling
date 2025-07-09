package com.carpooling.backend.controller;

import com.carpooling.backend.model.Ride;
import com.carpooling.backend.model.Ticket;
import com.carpooling.backend.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    @GetMapping("findRidesBetween/{from}/{to}")
    public List<Ride> findRidesBetween(@PathVariable(name = "from") String fromLocation, @PathVariable(name = "to") String toLocation){
        return service.findRidesBetween(fromLocation, toLocation);
    }

    @GetMapping("/bookRide/{rideId}/{fromStop}/{toStop}/{passengerId}")
    public Ticket bookRide(@PathVariable String rideId, @PathVariable String fromStop, @PathVariable String toStop, @PathVariable String passengerId){
        return service.bookRide(rideId,fromStop,toStop,passengerId);
    }

    @PutMapping("/cancelRide/{ticketId}")
    public boolean cancelRide(@PathVariable String ticketId){
        return service.cancelRide(ticketId);
    }
}
