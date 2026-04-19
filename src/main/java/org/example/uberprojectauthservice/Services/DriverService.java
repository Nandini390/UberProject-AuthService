package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Dtos.DriverDto;
import org.example.uberprojectauthservice.Dtos.UpdateDriverDto;

import java.util.List;

public interface DriverService {
    DriverDto updateDriver(String driverId, UpdateDriverDto dto);
    List<DriverDto> getAllDriver();
}
