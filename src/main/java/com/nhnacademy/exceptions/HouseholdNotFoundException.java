package com.nhnacademy.exceptions;

public class HouseholdNotFoundException extends RuntimeException{
    public HouseholdNotFoundException(String message) {
        super(message);
    }
}
