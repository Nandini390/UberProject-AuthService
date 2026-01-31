package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Dto.PassengerResponseDto;
import org.example.uberprojectauthservice.Dto.PassengerSignupRequestDto;
import org.example.uberprojectauthservice.Models.Passenger;
import org.example.uberprojectauthservice.Repositories.PassengerRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    PassengerRepository passengerRepository;

    public AuthService(PassengerRepository passengerRepository){
        this.passengerRepository=passengerRepository;
    }

    public PassengerResponseDto SignupPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
        Passenger passenger= Passenger.builder()
                .name(passengerSignupRequestDto.getName())
                .email(passengerSignupRequestDto.getEmail())
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .password(passengerSignupRequestDto.getPassword())
                .build();
        Passenger newPassenger = passengerRepository.save(passenger);
        return PassengerResponseDto.from(newPassenger);
    }
}
