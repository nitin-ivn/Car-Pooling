package com.carpooling.backend.service;


import com.carpooling.backend.dao.TicketRepo;
import com.carpooling.backend.model.Ticket;

import java.util.List;

public interface TicketService{
    List<Ticket> getTickets(String passengerId);
}
