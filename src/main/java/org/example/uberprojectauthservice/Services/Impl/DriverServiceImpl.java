package org.example.uberprojectauthservice.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Dtos.DriverDto;
import org.example.uberprojectauthservice.Dtos.UpdateDriverDto;
import org.example.uberprojectauthservice.Repositories.DriverRepository;
import org.example.uberprojectauthservice.Services.DriverService;
import org.example.uberprojectauthservice.exception.ResourceNotFoundException;
import org.example.uberprojectauthservice.helpers.UserHelper;
import org.example.uberprojectentityservice.Models.Driver;
import org.example.uberprojectentityservice.Models.DriverApprovalStatus;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DriverDto updateDriver(String driverId, UpdateDriverDto dto) {
        UUID uId = UserHelper.parseUUID(driverId);
        Driver driver = driverRepository.findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        if (dto.getName() != null) driver.getUser().setName(dto.getName());
        if (dto.getPassword() != null) driver.getUser().setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getPhoneNumber() != null) driver.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getLicenseNumber() != null) driver.setLicenseNumber(dto.getLicenseNumber());
        if (dto.getAadharCard() != null) driver.setAadharCard(dto.getAadharCard());
        if (dto.getIsAvailable() != null) driver.setIsAvailable(dto.getIsAvailable());
        if (dto.getRating() != null) driver.setRating(dto.getRating());
        if (dto.getActiveCity() != null) driver.setActiveCity(dto.getActiveCity());

        Driver saved = driverRepository.save(driver);
        return modelMapper.map(saved, DriverDto.class);
    }

    public List<DriverDto> getAllDriver(){
        List<Driver> drivers = driverRepository.findAll();
        return drivers.stream().map(d-> modelMapper.map(d, DriverDto.class)).toList();
    }

    @Override
    public DriverDto updateAvailability(String driverId, boolean available) {
        UUID uId = UserHelper.parseUUID(driverId);
        Driver driver = driverRepository.findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        driver.setIsAvailable(available);
        return modelMapper.map(driverRepository.save(driver), DriverDto.class);
    }

    @Override
    public List<DriverDto> getEligibleDrivers(String activeCity) {
        List<Driver> drivers;
        if (activeCity == null || activeCity.isBlank()) {
            drivers = driverRepository.findByDriverApprovalStatusAndIsAvailable(DriverApprovalStatus.APPROVED, true);
        } else {
            drivers = driverRepository.findByDriverApprovalStatusAndIsAvailableAndActiveCityIgnoreCase(
                    DriverApprovalStatus.APPROVED,
                    true,
                    activeCity
            );
        }
        return drivers.stream().map(driver -> modelMapper.map(driver, DriverDto.class)).toList();
    }

    @Override
    public List<DriverDto> getDriversByApprovalStatus(DriverApprovalStatus status) {
        return driverRepository.findByDriverApprovalStatus(status)
                .stream()
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .toList();
    }
}
