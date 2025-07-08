package com.nehruCollege.cinema.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.nehruCollege.cinema.config.PasswordManager;
import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.LoginUser;
import com.nehruCollege.cinema.model.User;
import com.nehruCollege.cinema.repository.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    
    private final PasswordManager passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordManager passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(User user) throws ServiceException {
    	if(user.getMobileNo()<=999999999&&user.getMobileNo()>9999999999L){
			throw new ServiceException("Mobile Number is invalid",HttpStatus.NOT_ACCEPTABLE);
		}
		if(user.getName().length()<=0){
			throw new ServiceException("Provide user name",HttpStatus.NOT_ACCEPTABLE);
		}
		if(user.getPassword().trim().length()<=0){
			throw new ServiceException("Password Strength Poor",HttpStatus.NOT_ACCEPTABLE);
		}
		if(userRepository.findByEmail(user.getEmail())==null) {
			user.setPassword(passwordEncoder.generateEncodedPassword(user.getPassword()));
			return userRepository.save(user);
			
		}
		else {
			throw new ServiceException("User's email Already Exist",HttpStatus.CONFLICT);
		}
    }

    public User authenticate(LoginUser input) throws ServiceException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(()-> new ServiceException("Invalid Email or Password"));
    }
}
