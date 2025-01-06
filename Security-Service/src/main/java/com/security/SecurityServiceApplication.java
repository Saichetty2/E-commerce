package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.security.client.VendorClientService;
import com.security.dto.Vendor;
import com.security.security.JwtAuthenticationFilter;
import com.security.service.AdminServiceImpl;

@SpringBootApplication
@Configuration
@EnableFeignClients(basePackages = "com.security.client")
public class SecurityServiceApplication implements CommandLineRunner {


	AdminServiceImpl adminServiceImpl= new AdminServiceImpl();
	

	 @Autowired
	 private VendorClientService vendorClientService;
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityServiceApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder encodePassword() {
		return	new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		
//		VendorDto findByEmail = vendorClientService.getByEmail("chnadra123@gmail.com");
//		System.out.println("Testing from the main application: 4 "+findByEmail);
		
		
		
	}
	
	

}
