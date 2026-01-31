package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Dto.PassengerResponseDto;
import org.example.uberprojectauthservice.Dto.PassengerSignupRequestDto;
import org.example.uberprojectauthservice.Models.Passenger;
import org.example.uberprojectauthservice.Repositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PassengerRepository passengerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.passengerRepository=passengerRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public PassengerResponseDto SignupPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
        Passenger passenger= Passenger.builder()
                .name(passengerSignupRequestDto.getName())
                .email(passengerSignupRequestDto.getEmail())
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword()))
                .build();
        Passenger newPassenger = passengerRepository.save(passenger);
        return PassengerResponseDto.from(newPassenger);
    }
}
