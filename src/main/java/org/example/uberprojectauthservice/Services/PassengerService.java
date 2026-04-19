package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Dtos.PassengerDto;
import org.example.uberprojectauthservice.Dtos.UpdatePassengerDto;

import java.util.List;

public interface PassengerService {
    PassengerDto updatePassenger(String passengerId, UpdatePassengerDto dto);
    List<PassengerDto> getAllPassenger();
}
