package com.carpooling.backend.service;

import com.carpooling.backend.dao.RideRepo;
import com.carpooling.backend.dao.TicketRepo;
import com.carpooling.backend.exceptions.CustomException;
import com.carpooling.backend.exceptions.RideNotFoundException;
import com.carpooling.backend.model.Ride;
import com.carpooling.backend.model.Stops;
import com.carpooling.backend.model.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideServiceImpl implements RideService{

    private final RideRepo repo;
    private final TicketRepo ticketRepo;

    public RideServiceImpl(RideRepo repo, TicketRepo ticketRepo) {
        this.repo = repo;
        this.ticketRepo = ticketRepo;
    }

    @Override
    public Ride saveRide(Ride ride){
        for (Stops stop : ride.getStops()) {
            stop.setVacancy(ride.getMaxNumberOfPassengers());
            stop.setRide(ride);
        }

        if(!validRideAndCanCreate(ride)){
            throw new CustomException("Another Ride already exists at same time");
        }

        return repo.save(ride);
    }


    private boolean validRideAndCanCreate(Ride ride) {
        String hostedId = ride.getUserId();

        //cant create two rides at same time
        List<Ride> hostedRides = repo.findByUserIdAndActiveTrue(hostedId);
        for (Ride ride1 : hostedRides){
            if (ride1.getDateOfRide().equals(ride.getDateOfRide()) &&
                    ride1.getTime().equals(ride.getTime())) {
                return false;
            }
        }
        return true;
    }

    @Override
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

    @Override
    @Transactional
    public Ticket bookRide(String rideId, String from, String to, String passengerId){

        Ride ride = repo.findById(rideId).orElseThrow(RideNotFoundException::new);

        if(!isValidAndCanBook(ride,passengerId)){
            throw new CustomException("Invalid ride");
        }
        List<Stops> stops = ride.getStops();

        List<String> stopNames = stops.stream().map(stops1 -> stops1.getStop().toLowerCase()).toList();

        int fromIndex = stopNames.indexOf(from.toLowerCase());
        int toIndex = stopNames.indexOf(to.toLowerCase());

        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            throw new CustomException("The required stops do not exist in this ride or in the wrong order");
        }

        for(int i = fromIndex; i< toIndex; i++){
            Stops s= stops.get(i);
            if(s.getVacancy() <= 0) throw new CustomException("There is no vacancy");
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
        ticket.setActive(true);
        ticket.setCancelled(false);
        ticketRepo.save(ticket);
        return ticket;
    }

    private boolean isValidAndCanBook(Ride ride, String passengerId) {

        //cant book his own ride
        if(ride.getUserId().equals(passengerId)) return false;

        //cant be a passenger and host at same time
        List<Ride> passengerRides = repo.findByUserIdAndActiveTrue(passengerId);
        for (Ride ride1 : passengerRides){
            if(!ride1.getRideId().equals(ride.getRideId()) &&
                ride1.getDateOfRide().equals(ride.getDateOfRide()) &&
                ride1.getTime().equals(ride.getTime())) throw new CustomException("Passenger is already hosting a ride at same time");
        }

        //passenger cant ride two at once
        List<Ticket> activeTickets = ticketRepo.findByPassengerIdAndActiveTrue(passengerId);
        for(Ticket t : activeTickets) {
            Ride bookedRide = repo.findById(t.getRideId()).orElse(null);
            if (bookedRide == null) continue;
            if (bookedRide.getDateOfRide().equals(ride.getDateOfRide()) && bookedRide.getTime().equals(ride.getTime())){
                throw new CustomException("Passenger already has a ride booked at same time");
            }
        }

        return true;
    }

    @Override
    @Transactional
    public boolean cancelPassengerRide(String ticketId){
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
            s.setVacancy(s.getVacancy() + 1);
        }

        //ride.setStops(stops);
        repo.save(ride);

        ticket.setCompleted(false);
        ticket.setActive(false);
        ticketRepo.save(ticket);
        return true;
    }

    @Override
    @Transactional
    public boolean cancelHostedRide(String rideId){
        Ride ride = repo.findById(rideId).orElse(null);
        if(ride == null) return false;

        List<Ticket> tickets = ticketRepo.findByRideId(rideId);
        for(Ticket ticket : tickets){
            if(!ticket.isActive()) continue;
            ticket.setCancelled(true);
            ticket.setActive(false);
            ticket.setCompleted(false);
            ticketRepo.save(ticket);
        }

        ride.setCancelled(true);
        repo.save(ride);

        return true;
    }

}
