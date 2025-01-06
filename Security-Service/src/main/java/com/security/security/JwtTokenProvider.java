package com.security.security;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;
	
	
	
//	generate token
	public String generateToken(Authentication authentication) {
		
		String userName = authentication.getName();
		 Instant now = Instant.now();
	        Instant validity = now.plus(jwtExpirationDate, ChronoUnit.MILLIS);	
		 
		String token = Jwts.builder()
		.setSubject(userName)
		.setIssuedAt(Date.from(now))
		.setExpiration(Date.from(validity))
		.signWith(key())
		.compact();
		
		System.out.println("GENERATING TOKEN: **************** "+ token);
		return token;
		
	}
	
//	generate key
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		
	}
	
//	get userName from jwt token
	public String getUserName(String token) {
		
	Claims claims= Jwts.parserBuilder()
		.setSigningKey(key())
		.build()
		.parseClaimsJws(token)
		.getBody();
	
	String userName = claims.getSubject();
	
	System.out.println("#########################: "+ userName);
		return userName;
		
	}

	//	validate token
	public boolean validateToken(String token) {
		Jwts.parserBuilder().setSigningKey(key())
		.build().parse(token);
		return true;
		
	}
	
	
	
	
    public Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
	

	

}
