package com.security.service;

import org.springframework.stereotype.Service;

import com.security.entity.Login;

@Service
public interface AuthService {
	
	String login(Login loginDto);
// we put string here to return the success message after login
}
