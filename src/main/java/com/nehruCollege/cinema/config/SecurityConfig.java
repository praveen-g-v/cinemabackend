package com.nehruCollege.cinema.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nehruCollege.cinema.filter.JwtAuthenticationFilter;
import com.nehruCollege.cinema.model.User;
import com.nehruCollege.cinema.service.UserService;



@Configuration
@EnableWebSecurity
public class SecurityConfig  {
	 private final AuthenticationProvider authenticationProvider;
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;
	 
	 public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,AuthenticationProvider authenticationProvider) {
		 this.authenticationProvider = authenticationProvider;
		 this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	 }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http
				.csrf(customizer -> customizer.disable())
				.authorizeHttpRequests(auth -> auth
		                .requestMatchers("/api/auth/**").permitAll()
		                .requestMatchers("/api/user/**").hasAuthority("ADMIN")
		                .anyRequest().authenticated()
		            )
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authenticationProvider(authenticationProvider)
					.addFilterBefore(
			                jwtAuthenticationFilter, 
			                UsernamePasswordAuthenticationFilter.class
			            )
					.build();
	}
	
	
//	@Bean
//	public UserDetailsService userDetailService() {
//		return new UserService();
//	}
//	
//	
//	
//	@Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }

}
