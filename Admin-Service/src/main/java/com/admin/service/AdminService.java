package com.admin.service;

import java.util.List;

import com.admin.entity.Admin;

public interface AdminService {
	
	Admin saveAdmin(Admin admin);
	
	List<Admin> getAllAdmins();
	
	Admin getOneAdminById(Long id);
	
	String deleteAdminById(Long id);
	Admin getByUsername(String username);

}
