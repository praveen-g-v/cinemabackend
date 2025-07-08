package com.nehruCollege.cinema.utils;

import org.springframework.stereotype.Component;

import com.nehruCollege.cinema.config.JwtTokenProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CookieUtil {
	
	private JwtTokenProvider jwtTokenProvider;
	
	public CookieUtil(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider=jwtTokenProvider;
	}
	
    
    public  Cookie addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie(jwtTokenProvider.getJwtRefreshTokenCookieName(), refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // For HTTPS only
        refreshTokenCookie.setPath("/api/auth/refresh");
        refreshTokenCookie.setMaxAge(32 * 24 * 60 * 60); // 32 days
        refreshTokenCookie.setAttribute("SameSite", "Strict"); // CSRF protection
        return refreshTokenCookie;
        
    }
    
    public  void deleteRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(0);
        
        response.addCookie(cookie);
    }
    
    public  String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(jwtTokenProvider.getJwtRefreshTokenCookieName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}