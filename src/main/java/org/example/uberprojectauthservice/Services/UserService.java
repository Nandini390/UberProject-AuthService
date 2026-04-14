package org.example.uberprojectauthservice.Services;



import org.example.uberprojectauthservice.Dtos.SignupRequestDto;
import org.example.uberprojectauthservice.Dtos.SignupResponseDto;
import org.example.uberprojectauthservice.Dtos.UpdateUserDto;
import org.example.uberprojectauthservice.Dtos.UserDto;
import org.example.uberprojectentityservice.Models.User;

import java.util.List;

public interface UserService {
    SignupResponseDto createUser(SignupRequestDto userDto) throws IllegalAccessException;
    SignupResponseDto completeProfile(String userId, SignupRequestDto dto);
    UserDto getUserByEmail(String email);
    UserDto updateUser(UpdateUserDto userDto, String userId);
    boolean deleteUser(String userId);
    UserDto getUserById(String userId);
    List<UserDto> getAllUsers();
}
