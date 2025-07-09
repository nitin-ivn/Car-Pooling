package com.carpooling.backend.dao;

import com.carpooling.backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {
    List<Ticket> findByPassengerId(String passengerId);
}
