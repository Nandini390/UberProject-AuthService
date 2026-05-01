package org.example.uberprojectauthservice.Services.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Dtos.SignupRequestDto;
import org.example.uberprojectauthservice.Dtos.SignupResponseDto;
import org.example.uberprojectauthservice.Dtos.UpdateUserDto;
import org.example.uberprojectauthservice.Dtos.UserDto;
import org.example.uberprojectauthservice.Repositories.DriverRepository;
import org.example.uberprojectauthservice.Repositories.PassengerRepository;
import org.example.uberprojectauthservice.Repositories.UserRepository;
import org.example.uberprojectauthservice.Services.UserService;
import org.example.uberprojectauthservice.exception.ResourceNotFoundException;
import org.example.uberprojectauthservice.exception.UserAlreadyExistsException;
import org.example.uberprojectauthservice.helpers.UserHelper;
import org.example.uberprojectentityservice.Models.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;

    @Override
    @Transactional
    public SignupResponseDto createUser(SignupRequestDto userDto){
        if(userDto.getEmail()==null || userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is required");
        }
        if (userDto.getName() == null || userDto.getName().isBlank()) {
            throw new RuntimeException("Name is required");
        }
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new UserAlreadyExistsException("Email already Exists");
        }
        if (userDto.getRole() == Role.DRIVER) {
            if (userDto.getLicenseNumber() == null || userDto.getLicenseNumber().isBlank()) {
                throw new RuntimeException("License number is required for drivers");
            }
            if (userDto.getAadharCard() == null || userDto.getAadharCard().isBlank()) {
                throw new RuntimeException("Aadhar card is required for drivers");
            }
        }
        User user=modelMapper.map(userDto, User.class);
        user.setProvider(Provider.LOCAL);
        User savedUser=userRepository.save(user);
        if (user.getRole() == Role.DRIVER) {
            Driver driver = Driver.builder()
                    .user(savedUser)
                    .phoneNumber(userDto.getPhoneNumber())
                    .licenseNumber(userDto.getLicenseNumber())
                    .aadharCard(userDto.getAadharCard())
                    .rating(0.0)
                    .isAvailable(false)
                    .driverApprovalStatus(DriverApprovalStatus.PENDING)
                    .activeCity(userDto.getActiveCity())
                    .build();

            driverRepository.save(driver);
        }else if (user.getRole() == Role.PASSENGER) {
            Passenger passenger = Passenger.builder()
                    .user(savedUser)
                    .phoneNumber(userDto.getPhoneNumber())
                    .rating(0.0)
                    .build();

            passengerRepository.save(passenger);
        }
        return new SignupResponseDto(
                "Signup successful",
                savedUser.getId().toString(),
                savedUser.getRole().toString(),
                user.getName()
        );
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user=userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user not found with given emailId"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto updateUser(UpdateUserDto userDto, String userId) {
        UUID uId=UserHelper.parseUUID(userId);
        User existingUser=userRepository.findById(uId).orElseThrow(()->new ResourceNotFoundException("user not found with given id"));
        if(userDto.getName()!=null) existingUser.setName(userDto.getName());
        //TODO:change password updation logic
        if(userDto.getPassword()!=null) existingUser.setPassword(userDto.getPassword());
        User updatedUser=userRepository.save(existingUser);
        return modelMapper.map(updatedUser,UserDto.class);
    }

    @Override
    public boolean deleteUser(String userId) {
       UUID uId = UserHelper.parseUUID(userId);
        Optional<Driver> driver = driverRepository.findByUserId(uId);
        driver.ifPresent(driverRepository::delete);
        Optional<Passenger> passenger = passengerRepository.findByUserId(uId);
        passenger.ifPresent(passengerRepository::delete);

        // Delete the user itself
        userRepository.deleteById(uId);
       return true;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user=userRepository.findById(UserHelper.parseUUID(userId)).orElseThrow(()->new ResourceNotFoundException("user with id not found"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user , UserDto.class))
                .toList();
    }

    @Override
    @Transactional
    public SignupResponseDto completeProfile(String userId, SignupRequestDto dto) {

        UUID uId = UserHelper.parseUUID(userId);
        User user = userRepository.findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Make sure only GUEST users can complete profile
        // Prevents already completed profiles from being overwritten
        if (user.getRole() != Role.GUEST) {
            throw new RuntimeException("Profile already completed");
        }

        // Validate role is provided
        if (dto.getRole() == null) {
            throw new RuntimeException("Role is required");
        }

        // Validate role specific fields
        if (dto.getRole() == Role.DRIVER) {
            if (dto.getLicenseNumber() == null || dto.getLicenseNumber().isBlank()) {
                throw new RuntimeException("License number is required for drivers");
            }
            if (dto.getAadharCard() == null || dto.getAadharCard().isBlank()) {
                throw new RuntimeException("Aadhar card is required for drivers");
            }
        }
        if (dto.getPhoneNumber() == null || dto.getPhoneNumber().isBlank()) {
            throw new RuntimeException("Phone number is required");
        }

        // Update User role from GUEST to actual role
        user.setRole(dto.getRole());
        User savedUser = userRepository.save(user);

        // Create role specific profile
        if (dto.getRole() == Role.DRIVER) {
            Driver driver = Driver.builder()
                    .user(savedUser)
                    .phoneNumber(dto.getPhoneNumber())
                    .licenseNumber(dto.getLicenseNumber())
                    .aadharCard(dto.getAadharCard())
                    .rating(0.0)
                    .isAvailable(false)
                    .driverApprovalStatus(DriverApprovalStatus.PENDING)
                    .activeCity(dto.getActiveCity())
                    .build();
            driverRepository.save(driver);

        } else if (dto.getRole() == Role.PASSENGER) {
            Passenger passenger = Passenger.builder()
                    .user(savedUser)
                    .phoneNumber(dto.getPhoneNumber())
                    .rating(0.0)
                    .build();
            passengerRepository.save(passenger);
        }

        return new SignupResponseDto(
                "Profile completed successfully",
                savedUser.getId().toString(),
                savedUser.getRole().toString(),
                savedUser.getName()
        );
    }
}