package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.entity.Login;
import com.security.security.JwtAuthResponse;
import com.security.service.AuthService;

@RestController
@RequestMapping("/login")
public class AuthController {
	
	@Autowired 
	private AuthService authService; 
	
	@GetMapping("/hello")
	public String home() {
		return "HELLO EVERYONE THIS IS NOT SECURE";
	}
	
	@PostMapping(value = {"/login","/signin"})
	public ResponseEntity<JwtAuthResponse> login(@RequestBody Login login){
		String token = authService.login(login);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return ResponseEntity.ok(jwtAuthResponse);
		
	}
	
	
	
	

}
