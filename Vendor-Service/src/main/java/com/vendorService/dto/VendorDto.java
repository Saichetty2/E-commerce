package com.vendorService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDto {

	
	private String vendorId;
	private String firmName;
	private String type;
	private String gstn;
	private String authPerson;
	private String email;
	private String password;
	private boolean active;
	private String   adminId;
	private String addedBy;
	private String role;
	
}