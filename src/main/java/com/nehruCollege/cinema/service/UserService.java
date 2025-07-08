package com.nehruCollege.cinema.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.User;
import com.nehruCollege.cinema.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	public User createUser(User user)throws ServiceException {
		if(user.getMobileNo()<=999999999&&user.getMobileNo()>9999999999L){
			throw new ServiceException("Mobile Number is invalid",HttpStatus.NOT_ACCEPTABLE);
		}
		if(user.getName().length()<=0){
			throw new ServiceException("Provide user name",HttpStatus.NOT_ACCEPTABLE);
		}
		if(user.getPassword().length()<=0){
			throw new ServiceException("Password Strength Poor",HttpStatus.NOT_ACCEPTABLE);
		}
		if(userRepository.findByEmail(user.getEmail())==null) {
			return userRepository.save(user);
			
		}
		else {
			throw new ServiceException("User's email Already Exist",HttpStatus.CONFLICT);
		}
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username.trim().length()==0){
			throw new UsernameNotFoundException("Invalid UserName");
		}
		User user=userRepository.findByEmail(username).orElseThrow(()->  new UsernameNotFoundException("Unable to fnd userName"));
		return user;
	}


	public User getUser(String email) {
		User user=userRepository.findByEmail(email).orElseThrow(()->  new UsernameNotFoundException("Unable to fnd userName"));
		return user;
	}
	
	

}
