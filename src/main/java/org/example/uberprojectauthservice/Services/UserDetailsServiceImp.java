package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Helpers.AuthPassengerDetails;
import org.example.uberprojectauthservice.Models.Passenger;
import org.example.uberprojectauthservice.Repositories.PassengerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*This class is responsible for loading the user in the form of UserDetails for Auth.
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final PassengerRepository passengerRepository;

    public UserDetailsServiceImp(PassengerRepository passengerRepository){
        this.passengerRepository=passengerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Passenger> passenger=passengerRepository.findPassengerByEmail(username);
        if(passenger.isPresent()) {
            return new AuthPassengerDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("Cannot find the passenger by the given Email");
        }
    }
}
