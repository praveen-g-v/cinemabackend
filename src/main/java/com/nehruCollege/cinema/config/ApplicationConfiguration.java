package com.nehruCollege.cinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nehruCollege.cinema.repository.UserRepository;
import com.nehruCollege.cinema.service.UserService;



@Configuration
public class ApplicationConfiguration {
	private final UserRepository userRepository;
	
	private final UserService userService;

    public ApplicationConfiguration(UserRepository userRepository,UserService userService) {
        this.userRepository = userRepository;
		this.userService =  userService;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return PasswordManager.getPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
