package org.example.uberprojectauthservice.Controllers;

import lombok.Getter;
import org.example.uberprojectauthservice.Dto.AuthRequestDto;
import org.example.uberprojectauthservice.Dto.PassengerResponseDto;
import org.example.uberprojectauthservice.Dto.PassengerSignupRequestDto;
import org.example.uberprojectauthservice.Services.AuthService;
import org.example.uberprojectauthservice.Services.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JWTService jwtService){
        this.authService=authService;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerResponseDto> signup(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerResponseDto response= authService.SignupPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signIn/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            Map<String,Object> payload=new HashMap<>();
            payload.put("email",authRequestDto.getEmail());
            payload.put("password",authRequestDto.getPassword());
            String jwtToken=jwtService.createToken(authRequestDto.getEmail());
            return new ResponseEntity<>(jwtToken,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Auth not successfull", HttpStatus.OK);
        }
    }
}
