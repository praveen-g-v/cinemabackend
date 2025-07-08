package com.nehruCollege.cinema.config;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	//private  final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	
	@Value("${app.jwt.secret}")
    private String jwtSecret;
    
    @Value("${app.jwt.access-token-expiration-ms}")
    private  long jwtAccessTokenExpirationMs;
    
    @Value("${app.jwt.refresh-token-expiration-ms}")
    private  long jwtRefreshTokenExpirationMs;
    
    @Value("${app.jwt.access-token-cookie-name}")
    private  String jwtAccessTokenCookieName;
    
    @Value("${app.jwt.refresh-token-cookie-name}")
    private  String jwtRefreshTokenCookieName;
    
    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    
    public String generateAccessToken(String email) {
        return generateTokenFromEmail(email, jwtAccessTokenExpirationMs);
    }
    
    public String generateRefreshToken(String email) {
        return generateTokenFromEmail(email, jwtRefreshTokenExpirationMs);
    }
    
    private String generateTokenFromEmail(String email, long expirationMs) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationMs))
                .signWith( key(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
          //  logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            //logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
           // logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            //logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    
    public ResponseCookie generateAccessTokenCookie(String token) {
        return generateCookie(jwtAccessTokenCookieName, token, "/api");
    }
    
    public ResponseCookie generateRefreshTokenCookie(String token) {
        return generateCookie(jwtRefreshTokenCookieName, token, "/api/auth/refresh-token");
    }
    
    public ResponseCookie getCleanAccessTokenCookie() {
        return ResponseCookie.from(jwtAccessTokenCookieName, "").path("/api").build();
    }
    
    public ResponseCookie getCleanRefreshTokenCookie() {
        return ResponseCookie.from(jwtRefreshTokenCookieName, "").path("/api/auth/refresh-token").build();
    }
    
 // Add these getter methods
    public  String getJwtAccessTokenCookieName() {
        return jwtAccessTokenCookieName;
    }
    
    public  String getJwtRefreshTokenCookieName() {
        return jwtRefreshTokenCookieName;
    }
    
    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value)
                .path(path)
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .build();
    }


	public  long getExpirationTime() {
		return jwtAccessTokenExpirationMs;
	}
	
	public  long getRefreshTokenExpirationTime() {
		return jwtRefreshTokenExpirationMs;
	}
}
