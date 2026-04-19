package org.example.uberprojectauthservice.Services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Dtos.SignupRequestDto;
import org.example.uberprojectauthservice.Dtos.SignupResponseDto;
import org.example.uberprojectauthservice.Services.AuthService;
import org.example.uberprojectauthservice.Services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignupResponseDto registerUser(SignupRequestDto userDto) throws IllegalAccessException {
        //verify email
        //verify password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userService.createUser(userDto);
    }
}
