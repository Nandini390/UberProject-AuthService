package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Dtos.DriverDto;
import org.example.uberprojectauthservice.Dtos.UpdateDriverDto;
import org.example.uberprojectentityservice.Models.DriverApprovalStatus;

import java.util.List;

public interface DriverService {
    DriverDto updateDriver(String driverId, UpdateDriverDto dto);
    List<DriverDto> getAllDriver();
    DriverDto updateAvailability(String driverId, boolean available);
    List<DriverDto> getEligibleDrivers(String activeCity);
    List<DriverDto> getDriversByApprovalStatus(DriverApprovalStatus status);
}
