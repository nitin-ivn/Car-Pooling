package com.carpooling.backend.model;

import com.carpooling.backend.service.MyGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Data
@Table(name = "tickets")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(generator = MyGenerator.generatorName)
    @GenericGenerator(name = MyGenerator.generatorName, strategy = "com.carpooling.backend.service.MyGenerator")
    private String ticketId;

    private String rideId;
    private String hostedUserId;
    private String fromStop;
    private String toStop;
    private String passengerId;
    private boolean completed;
    private boolean cancelled;
    private boolean active;
}
