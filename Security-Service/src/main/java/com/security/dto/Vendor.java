package com.security.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
	
	
	private String id;
	private String firmName;
	private String type;
	private String gstn;
	private String authPerson;
	private String email;
	private String password;
	private Long   adminId;
	private String addedBy;
	private String role;

}
