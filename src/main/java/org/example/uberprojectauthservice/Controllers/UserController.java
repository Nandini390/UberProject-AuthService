package org.example.uberprojectauthservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Dtos.SignupRequestDto;
import org.example.uberprojectauthservice.Dtos.SignupResponseDto;
import org.example.uberprojectauthservice.Dtos.UpdateUserDto;
import org.example.uberprojectauthservice.Dtos.UserDto;
import org.example.uberprojectauthservice.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SignupResponseDto> createUser(@RequestBody SignupRequestDto userDto) throws Exception {
        SignupResponseDto response = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
       UserDto user=userService.getUserById(userId);
       return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/email/{e}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("e") String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    @PreAuthorize("hasRole(ADMIN)")
    @DeleteMapping("/{userId}")
    public boolean deleteUser(@PathVariable String userId){

        boolean response=userService.deleteUser(userId);
        return response;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @RequestBody UpdateUserDto userDto){
      UserDto response=userService.updateUser(userDto,userId);
      return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/{userId}/complete-profile")
    public ResponseEntity<SignupResponseDto> completeProfile(
            @PathVariable String userId,
            @RequestBody SignupRequestDto dto) {
        SignupResponseDto response = userService.completeProfile(userId, dto);
        return ResponseEntity.ok(response);
    }
}
