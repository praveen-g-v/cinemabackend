package com.nehruCollege.cinema.controller;

import java.net.http.HttpResponse;
import java.util.Date;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nehruCollege.cinema.config.PasswordManager;
import com.nehruCollege.cinema.enums.Roles;
import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.User;
import com.nehruCollege.cinema.repository.UserRepository;
import com.nehruCollege.cinema.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordManager passwordManager;
	
	@PostMapping
	public ResponseEntity<String> addNewUser(
			
			@RequestParam(value = "name" , required = true)
			String name,
			@RequestParam(value = "email" , required = true)
			String email,
			@RequestParam(value = "password" , required = true)
			String password,
			@RequestParam(value = "mobileNo" , required = true)
			long mobileNo
			)throws ServiceException{
		User user=new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setMobileNo(mobileNo);
		user.setCreatedOn(new Date());
		userService.createUser(user);
		return new ResponseEntity<>("Created Successfully",HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public User getUsersCount(
			@RequestParam(value = "email" , required = true)
	String email) {
		User user=userService.getUser(email);
		return user;
	}
}
