package com.carpooling.backend.service;

import com.carpooling.backend.dao.RideRepo;
import com.carpooling.backend.model.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RideService {

    @Autowired
    RideRepo repo;


    public Ride saveRide(Ride ride){
        return repo.save(ride);
    }

    public Ride getRide(String rideId) {
        return repo.findById(rideId).orElse(new Ride());
    }
}
