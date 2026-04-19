package org.example.uberprojectauthservice.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Dtos.PassengerDto;
import org.example.uberprojectauthservice.Dtos.UpdatePassengerDto;
import org.example.uberprojectauthservice.Repositories.PassengerRepository;
import org.example.uberprojectauthservice.Services.PassengerService;
import org.example.uberprojectauthservice.exception.ResourceNotFoundException;
import org.example.uberprojectauthservice.helpers.UserHelper;
import org.example.uberprojectentityservice.Models.Passenger;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PassengerDto updatePassenger(String passengerId, UpdatePassengerDto dto) {
        UUID uId = UserHelper.parseUUID(passengerId);
        Passenger passenger = passengerRepository.findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found"));

        if (dto.getName() != null) passenger.getUser().setName(dto.getName());
        if (dto.getPassword() != null) passenger.getUser().setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getPhoneNumber() != null) passenger.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getRating() != null) passenger.setRating(dto.getRating());

        Passenger saved = passengerRepository.save(passenger);
        return modelMapper.map(saved, PassengerDto.class);
    }

    public List<PassengerDto> getAllPassenger(){
        List<Passenger> passengers = passengerRepository.findAll();
        return passengers.stream()
                .map(p -> modelMapper.map(p, PassengerDto.class))
                .toList();
    }
}
