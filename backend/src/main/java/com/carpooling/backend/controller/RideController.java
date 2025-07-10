package com.carpooling.backend.controller;

import com.carpooling.backend.exceptions.CustomException;
import com.carpooling.backend.model.Ride;
import com.carpooling.backend.model.Ticket;
import com.carpooling.backend.service.RideServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RideController {

    @Autowired
    private RideServiceImpl service;

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
    public ResponseEntity<?> bookRide(@PathVariable String rideId, @PathVariable String fromStop, @PathVariable String toStop, @PathVariable String passengerId) throws CustomException {
        Ticket ticket = service.bookRide(rideId,fromStop,toStop,passengerId);
        return ResponseEntity.ok(ticket);

    }

    @PutMapping("/cancelRide/{ticketId}")
    public boolean cancelRide(@PathVariable String ticketId){
        return service.cancelRide(ticketId);
    }
}
