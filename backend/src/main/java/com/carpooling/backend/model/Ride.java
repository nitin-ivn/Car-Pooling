package com.carpooling.backend.model;

import com.carpooling.backend.service.MyGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Data
@Table(name = "rides")
public class Ride implements Serializable {

    @Id
    @GeneratedValue(generator = MyGenerator.generatorName)
    @GenericGenerator(name = MyGenerator.generatorName, strategy = "com.carpooling.backend.service.MyGenerator")
    private String rideId;


    private String userId;
    private String fromLocation;
    private String toLocation;
    private LocalDate dateOfRide;
    private LocalTime time;
    private double price;
    private boolean active;
    private int noOfPassengers;
    private int maxNumberOfPassengers;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL)
    @OrderBy("stopId ASC")
    private List<Stops> stops;
}
