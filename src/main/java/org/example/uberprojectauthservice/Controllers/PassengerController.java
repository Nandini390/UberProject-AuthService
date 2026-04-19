package org.example.uberprojectauthservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Dtos.PassengerDto;
import org.example.uberprojectauthservice.Dtos.UpdatePassengerDto;
import org.example.uberprojectauthservice.Services.PassengerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;

    @PatchMapping("/update/{passengerId}")
    public ResponseEntity<PassengerDto> updatePassenger(
            @PathVariable String passengerId,
            @RequestBody UpdatePassengerDto dto) {
        PassengerDto response = passengerService.updatePassenger(passengerId, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PassengerDto>> getAllPassenger(){
        List<PassengerDto> passengers = passengerService.getAllPassenger();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }
}
