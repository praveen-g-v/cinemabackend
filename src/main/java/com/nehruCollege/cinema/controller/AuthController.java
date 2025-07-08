package com.nehruCollege.cinema.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nehruCollege.cinema.config.JwtTokenProvider;
import com.nehruCollege.cinema.config.PasswordManager;
import com.nehruCollege.cinema.config.UserDetailsImpl;
import com.nehruCollege.cinema.enums.Roles;
import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.LoginResponse;
import com.nehruCollege.cinema.model.LoginUser;
import com.nehruCollege.cinema.model.User;
import com.nehruCollege.cinema.service.AuthenticationService;
import com.nehruCollege.cinema.service.JwtService;
import com.nehruCollege.cinema.service.UserService;
import com.nehruCollege.cinema.utils.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final JwtTokenProvider tokenProvider;
    
    private final AuthenticationService authenticationService;
    
    private final CookieUtil cookieUtil;

    public AuthController(JwtTokenProvider tokenProvider, AuthenticationService authenticationService,CookieUtil cookieUtil) {
        this.tokenProvider = tokenProvider;
        this.authenticationService = authenticationService;
		this.cookieUtil = cookieUtil;
    }
   
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(	@RequestParam(value = "email" , required = true)
			String email,
			@RequestParam(value = "password" , required = true)
			String password,
			HttpServletResponse response
	) throws ServiceException {
    	LoginUser loginUserDto=new LoginUser();
    	loginUserDto.setEmail(email);
    	loginUserDto.setPassword(password);	
    	User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = tokenProvider.generateAccessToken(authenticatedUser.getEmail());
        String refreshToken=tokenProvider.generateRefreshToken(authenticatedUser.getEmail());
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setToken(jwtToken);
//        loginResponse.setExpiresIn(tokenProvider.getExpirationTime());
        System.out.println(cookieUtil.addRefreshTokenCookie(response, refreshToken).toString());
        Cookie cookie=cookieUtil.addRefreshTokenCookie(response, refreshToken);
        
        return ResponseEntity.ok()
        		.header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + jwtToken)
                .body("Success fully logged in");
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
			@RequestParam(value = "name" , required = true)
			String name,
			@RequestParam(value = "email" , required = true)
			String email,
			@RequestParam(value = "password" , required = true)
			String password,
			@RequestParam(value = "mobileNo" , required = true)
			long mobileNo
			)throws ServiceException{
    	
    	System.out.println("Registering user");
		User user=new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setMobileNo(mobileNo);
		List<String> userRole=new ArrayList<>();
		userRole.add("USER");
		user.setRole(userRole);
		user.setCreatedOn(new Date());
		authenticationService.signup(user);
        
        return ResponseEntity.ok("User registered successfully!");
    }
//    
//    @PostMapping("/refresh-token")
//    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh-token") String refreshToken) {
//        if (!tokenProvider.validateToken(refreshToken)) {
//            return ResponseEntity.badRequest().body("Invalid refresh todsken");
//        }
//        
//        String email = tokenProvider.getEmailFromToken(refreshToken);
//        
//        String newAccessToken = tokenProvider.generateAccessToken(email);
//        ResponseCookie accessCookie = tokenProvider.generateAccessTokenCookie(newAccessToken);
//        
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
//                .body("Token refreshed successfully");
//    }
//    
//    @PostMapping("/logout")
//    public ResponseEntity<?> logoutUser() {
//        ResponseCookie accessCookie = tokenProvider.getCleanAccessTokenCookie();
//        ResponseCookie refreshCookie = tokenProvider.getCleanRefreshTokenCookie();
//        
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
//                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
//                .body("You've been logged out!");
//    }
}
