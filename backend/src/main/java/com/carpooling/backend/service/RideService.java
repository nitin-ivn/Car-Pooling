package com.carpooling.backend.service;

import com.carpooling.backend.dao.RideRepo;
import com.carpooling.backend.dao.TicketRepo;
import com.carpooling.backend.model.Ride;
import com.carpooling.backend.model.Stops;
import com.carpooling.backend.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideService {

    @Autowired
    RideRepo repo;

    @Autowired
    TicketRepo ticketRepo;


    public Ride saveRide(Ride ride){
        for (Stops stop : ride.getStops()) {
            stop.setVacancy(ride.getMaxNumberOfPassengers());
            stop.setRide(ride);
        }

        return repo.save(ride);
    }

    public Ride getRide(String rideId) {
        return repo.findById(rideId).orElse(new Ride());
    }

    public List<Ride> findRidesBetween(String from, String to){
        List<Ride> allRides = repo.findAll();
        List<Ride> matchingRides = new ArrayList<>();

        for(Ride ride: allRides){
            List<Stops> stops = ride.getStops();

            List<String> stopNames = stops.stream().map(stops1 -> stops1.getStop().toLowerCase()).toList();

            int fromIndex = stopNames.indexOf(from.toLowerCase());
            int toIndex = stopNames.indexOf(to.toLowerCase());

            if(fromIndex != -1 && toIndex != -1 && fromIndex < toIndex){
                matchingRides.add(ride);
            }
        }
        return matchingRides;
    }

    public Ticket bookRide(String rideId, String from, String to, String passengerId){
        Ride ride = repo.findById(rideId).orElse(null);
        if(ride == null) return null;
        List<Stops> stops = ride.getStops();

        List<String> stopNames = stops.stream().map(stops1 -> stops1.getStop().toLowerCase()).toList();

        int fromIndex = stopNames.indexOf(from.toLowerCase());
        int toIndex = stopNames.indexOf(to.toLowerCase());

        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            return null;
        }

        for(int i = fromIndex; i< toIndex; i++){
            Stops s= stops.get(i);
            if(s.getVacancy() <= 0) return null;
            s.setVacancy(s.getVacancy() - 1);
        }
        repo.save(ride);

        Ticket ticket = new Ticket();
        ticket.setRideId(rideId);
        ticket.setHostedUserId(ride.getUserId());
        ticket.setFromStop(from);
        ticket.setToStop(to);
        ticket.setPassengerId(passengerId);
        ticket.setCompleted(true);
        ticketRepo.save(ticket);
        return ticket;
    }

    public boolean cancelRide(String ticketId){
        Ticket ticket = ticketRepo.findById(ticketId).orElse(null);
        if (ticket == null || !ticket.isCompleted()) return false;

        Ride ride = repo.findById(ticket.getRideId()).orElse(null);
        if(ride == null) return false;
        List<Stops> stops = ride.getStops();

        List<String> stopNames = stops.stream().map(stops1 -> stops1.getStop().toLowerCase()).toList();

        int fromIndex = stopNames.indexOf(ticket.getFromStop().toLowerCase());
        int toIndex = stopNames.indexOf(ticket.getToStop().toLowerCase());

        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            return false;
        }
        for(int i = fromIndex; i< toIndex; i++){
            Stops s= stops.get(i);
            if(s.getVacancy() <= 0) return false;
            s.setVacancy(s.getVacancy() + 1);
        }
        repo.save(ride);

        ticket.setCompleted(false);
        ticketRepo.save(ticket);
        return true;
    }
}
