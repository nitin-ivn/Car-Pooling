package com.carpooling.backend.service;

import com.carpooling.backend.exceptions.CustomException;
import com.carpooling.backend.model.Ride;
import com.carpooling.backend.model.Ticket;

import javax.print.DocFlavor;

public interface RideService {

    Ride saveRide(Ride ride);


    Ride getRide(String rideId);

    Ticket bookRide(String rideId, String from, String to, String passengerId) throws CustomException;


    boolean cancelPassengerRide(String ticketId);

    boolean cancelHostedRide(String rideId);
}
