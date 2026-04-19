package org.example.uberprojectauthservice.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Dtos.DriverDto;
import org.example.uberprojectauthservice.Repositories.DriverRepository;
import org.example.uberprojectauthservice.Services.AdminService;
import org.example.uberprojectauthservice.exception.ResourceNotFoundException;
import org.example.uberprojectentityservice.Models.Driver;
import org.example.uberprojectentityservice.Models.DriverApprovalStatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<DriverDto> getDriversByStatus(DriverApprovalStatus status) {
        return driverRepository.findByDriverApprovalStatus(status)
                .stream()
                .map(this::mapToDriverDto)
                .toList();
    }

    @Override
    public DriverDto getDriverById(String driverId) {
        Driver driver = driverRepository.findById(UUID.fromString(driverId)).orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        return mapToDriverDto(driver);
    }
    @Override
    public DriverDto updateDriverStatus(String driverId, DriverApprovalStatus status) {
        Driver driver = driverRepository.findById(UUID.fromString(driverId))
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        // Cannot approve an already denied driver directly
        // Admin must review again
        if (driver.getDriverApprovalStatus() == DriverApprovalStatus.DENIED
                && status == DriverApprovalStatus.APPROVED) {
            throw new RuntimeException("Cannot approve a denied driver directly. Please review first.");
        }

        driver.setDriverApprovalStatus(status);
        Driver savedDriver = driverRepository.save(driver);
        return mapToDriverDto(savedDriver);
    }

    @Override
    public DriverDto denyDriver(String driverId, String reason) {
        Driver driver = driverRepository.findById(UUID.fromString(driverId))
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        if (driver.getDriverApprovalStatus() == DriverApprovalStatus.APPROVED) {
            throw new RuntimeException("Cannot deny an already approved driver");
        }

        driver.setDriverApprovalStatus(DriverApprovalStatus.DENIED);
        driver.setDenialReason(reason);  // store reason for denial
        Driver savedDriver = driverRepository.save(driver);
        return mapToDriverDto(savedDriver);
    }
    // maps Driver + User email into DriverDto
    private DriverDto mapToDriverDto(Driver driver) {
        DriverDto dto = modelMapper.map(driver, DriverDto.class);
        dto.setEmail(driver.getUser().getEmail());  // pull email from linked User
        return dto;
    }
}
