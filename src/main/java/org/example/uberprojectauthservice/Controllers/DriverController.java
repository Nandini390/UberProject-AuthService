package org.example.uberprojectauthservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Dtos.DriverDto;
import org.example.uberprojectauthservice.Dtos.UpdateDriverDto;
import org.example.uberprojectauthservice.Services.DriverService;
import org.example.uberprojectentityservice.Models.DriverApprovalStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PatchMapping("/update/{driverId}")
    public ResponseEntity<DriverDto> updateDriver(
            @PathVariable String driverId,
            @RequestBody UpdateDriverDto dto) {
        DriverDto response = driverService.updateDriver(driverId, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DriverDto>> getAllDrivers(){
        List<DriverDto> drivers = driverService.getAllDriver();
        return new ResponseEntity<>(drivers,HttpStatus.OK);
    }

    @PatchMapping("/{driverId}/availability")
    public ResponseEntity<DriverDto> updateAvailability(@PathVariable String driverId, @RequestParam boolean available) {
        return ResponseEntity.ok(driverService.updateAvailability(driverId, available));
    }

    @GetMapping("/eligible")
    public ResponseEntity<List<DriverDto>> getEligibleDrivers(@RequestParam(required = false) String city) {
        return ResponseEntity.ok(driverService.getEligibleDrivers(city));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DriverDto>> getDriversByStatus(@PathVariable DriverApprovalStatus status) {
        return ResponseEntity.ok(driverService.getDriversByApprovalStatus(status));
    }
}
