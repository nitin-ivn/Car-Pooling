package com.carpooling.backend.dao;

import com.carpooling.backend.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RideRepo extends JpaRepository<Ride, String> {
    List<Ride> findByUserIdAndActiveTrue(String userId);

    List<Ride> findByActiveTrue();
}
