package com.nehruCollege.cinema.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.User;
import com.nehruCollege.cinema.repository.UserRepository;



public class CustomAuthProvider implements AuthenticationProvider {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordManager passwordManager;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user=userRepository.findByEmail(username).orElseThrow(()->{
        	return new BadCredentialsException("Authentication failed");
        });
        boolean isValid=passwordManager.validatePassword(password, user.getPassword());
        if(isValid) {
        	return new UsernamePasswordAuthenticationToken(
                    username, 
                    password, 
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()))
                );
        }else {
            throw new BadCredentialsException("Authentication failed");
        }
//        System.out.println(username+"   "+password);
//        // Implement your custom authentication logic here
//        if ("admin".equals(username) && "secret".equals(password)) {
//            return new UsernamePasswordAuthenticationToken(
//                username, 
//                password, 
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
//            );
//        } else {
//            throw new BadCredentialsException("Authentication failed");
//        }
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
