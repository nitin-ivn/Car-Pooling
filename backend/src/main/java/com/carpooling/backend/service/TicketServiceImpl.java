package com.carpooling.backend.service;

import com.carpooling.backend.dao.TicketRepo;
import com.carpooling.backend.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    TicketRepo repo;

    @Override
    public List<Ticket> getTickets(String passengerId) {
        return repo.findByPassengerId(passengerId);
    }
}
