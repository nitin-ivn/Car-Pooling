package com.carpooling.backend.controller;

import com.carpooling.backend.model.Ticket;
import com.carpooling.backend.service.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TicketController {

    @Autowired
    TicketServiceImpl service;

    @GetMapping("/MyTickets/{passengerId}")
    public List<Ticket> getMyTickets(@PathVariable String passengerId){
        return service.getTickets(passengerId);
    }
}
