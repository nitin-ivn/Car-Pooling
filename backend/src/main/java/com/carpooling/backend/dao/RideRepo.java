package com.carpooling.backend.dao;

import com.carpooling.backend.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepo extends JpaRepository<Ride, String> {
}
