package com.carpooling.backend.controller;

import com.carpooling.backend.exceptions.CustomException;
import com.carpooling.backend.model.Ride;
import com.carpooling.backend.model.Ticket;
import com.carpooling.backend.service.RideServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RideController {

    private final RideServiceImpl service;

    public RideController(RideServiceImpl service) {
        this.service = service;
    }

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
        System.out.println(fromLocation + " " + toLocation);
        return service.findRidesBetween(fromLocation, toLocation);
    }

    @GetMapping("/bookRide/{rideId}/{fromStop}/{toStop}/{passengerId}")
    public ResponseEntity<?> bookRide(@PathVariable String rideId, @PathVariable String fromStop, @PathVariable String toStop, @PathVariable String passengerId){
        try{
            Ticket ticket = service.bookRide(rideId,fromStop,toStop,passengerId);
            return ResponseEntity.ok(ticket);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/cancelPassengerRide/{ticketId}")
    public boolean cancelPassengerRide(@PathVariable String ticketId){
        return service.cancelPassengerRide(ticketId);
    }

    @DeleteMapping("/cancelHostedRide/{rideId}")
    public boolean cancelHostedRide(@PathVariable String rideId){
        return service.cancelHostedRide(rideId);
    }
}
