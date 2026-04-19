package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Dtos.SignupRequestDto;
import org.example.uberprojectauthservice.Dtos.SignupResponseDto;

public interface AuthService {
    SignupResponseDto registerUser(SignupRequestDto userDto) throws IllegalAccessException;
}
