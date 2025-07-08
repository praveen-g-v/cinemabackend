package com.nehruCollege.cinema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordManager {

	
	/**
	 * Need to write code for encrypting the password for user and validation as well. 
	 * 
	 */
	@Value("${passwordStrength}")
	private static int passwordStrength;
	
//	public int getPasswordStrength() {
//		return passwordStrength;
//	}
	
	
	private final static BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(passwordStrength==0?15:passwordStrength);
	
	public static BCryptPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	
	public String generateEncodedPassword(String password) {
		
		return passwordEncoder.encode(password);
	}
	
	
	public boolean validatePassword(String rawPassword,String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	
	
	
}
