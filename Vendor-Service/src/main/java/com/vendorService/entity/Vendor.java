package com.vendorService.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
	
	@Id
	private String vendorId;
	private String firmName;
	private String type;
	private String gstn;
	private String authPerson;
	
	@Column(unique = true)
	private String email;
	private String password;
	private boolean active;
	private String   adminId;
	private String addedBy;
	private String role;
	
	
	public void setId() {
		
		this.vendorId= UUID.randomUUID().toString().replace("-", "");
		
	}

}
