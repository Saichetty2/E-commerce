package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.security.JwtAuthenticationEntryPoint;
import com.security.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
//	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter  authenticationFilter;
	
	
	public SecurityConfig (JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter authenticationFilter ) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationFilter=authenticationFilter;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
		
	}
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf)-> csrf.disable())
		.authorizeHttpRequests((authorize)-> authorize
				 .requestMatchers("/login/**", "/admin/getuserid").permitAll()
				.anyRequest().authenticated()).exceptionHandling(exception -> 
				exception.authenticationEntryPoint(authenticationEntryPoint)).sessionManagement(session -> 
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		.httpBasic(Customizer.withDefaults());
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails sai = User.builder()
//				.username("saipatel")
//				.password(passwordEncoder().encode("sai"))
//				.roles("ADMIN")
//				.build();
//		
//		UserDetails praneeth = User.builder()
//				.username("praneeth")
//				.password(passwordEncoder().encode("pra"))
//				.roles("HR")
//				.build();
//		return new InMemoryUserDetailsManager(sai, praneeth);
//		
//	}

}
