package com.security.superadmin.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperAdminEntity {
	
	private Long id;
	private String Username;
	private String password;
	private String role;
	

}
