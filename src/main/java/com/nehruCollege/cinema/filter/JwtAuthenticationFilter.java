package com.nehruCollege.cinema.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.nehruCollege.cinema.config.JwtTokenProvider;
import com.nehruCollege.cinema.utils.CookieUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final HandlerExceptionResolver handlerExceptionResolver;
	private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
    private final CookieUtil cookieUtil;

    
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver,CookieUtil cookieUtil) {
        this.handlerExceptionResolver = handlerExceptionResolver;
		this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
		this.cookieUtil = cookieUtil;
    }
    
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        Cookie[] cookies = request.getCookies();
        Arrays.asList(cookies).stream().forEach(System.out::println);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenProvider.getJwtAccessTokenCookieName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) 
                                    throws ServletException, IOException {
    	final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        
        try {
            // Extract access token from Authorization header
            final String jwt = getJwtFromRequest(request);
            final String userEmail = tokenProvider.getEmailFromToken(jwt);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (userEmail != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (tokenProvider.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }
//            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//                Authentication authentication = tokenProvider.getAuthentication(jwt);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
        } catch (ExpiredJwtException ex) {
            // Handle token expiration (might trigger refresh)
            String refreshToken = getRefreshTokenFromCookies(request);
            if (refreshToken != null && tokenProvider.validateToken(refreshToken)) {
                // Generate new tokens and set them
                String newAccessToken = tokenProvider.generateAccessToken(tokenProvider.getEmailFromToken(refreshToken));
                String newRefreshToken = tokenProvider.generateRefreshToken(tokenProvider.getEmailFromToken(refreshToken));
                final String userEmail = tokenProvider.getEmailFromToken(newAccessToken);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (userEmail != null && authentication == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                    if (tokenProvider.validateToken(newAccessToken)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }

                }
                response.setHeader("Authorization", "Bearer " + newAccessToken);
                response.addCookie(cookieUtil.addRefreshTokenCookie(response, newRefreshToken));
                
                } else {
                // No valid refresh token - force login
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired");
                return;
            }
        }
        catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }

        
        filterChain.doFilter(request, response);
    }
    
    private String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenProvider.getJwtRefreshTokenCookieName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
