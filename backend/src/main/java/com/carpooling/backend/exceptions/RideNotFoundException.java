package com.carpooling.backend.exceptions;

public class RideNotFoundException extends RuntimeException{
    public RideNotFoundException(){
        super("Ride Not Found");
    }
}
