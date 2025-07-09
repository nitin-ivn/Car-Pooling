package com.carpooling.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
public class Stops {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int stopId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    private String stop;
    private int vacancy;
}
