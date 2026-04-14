package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Dtos.DriverDto;
import org.example.uberprojectentityservice.Models.DriverApprovalStatus;

import java.util.List;

public interface AdminService {
    List<DriverDto> getDriversByStatus(DriverApprovalStatus status);
    DriverDto getDriverById(String driverId);
    DriverDto updateDriverStatus(String driverId, DriverApprovalStatus status);
    DriverDto denyDriver(String driverId, String reason);
}
