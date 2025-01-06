package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.security.entity.Login;
import com.security.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {
	
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	

	@Override
	public String login(Login login) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				login.getUserName(), login.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		String token = jwtTokenProvider.generateToken(authenticate);
		
		return token; 
		
	}
	

}
