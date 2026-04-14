package org.example.uberprojectauthservice.security;

import lombok.RequiredArgsConstructor;
import org.example.uberprojectauthservice.Repositories.UserRepository;
import org.example.uberprojectauthservice.exception.ResourceNotFoundException;
import org.example.uberprojectentityservice.Models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user =  userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("Invalid Usename"));
       return new CustomUserDetails(user);
    }
}
