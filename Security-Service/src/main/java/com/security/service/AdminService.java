package com.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.security.dto.ProductDto;
import com.security.dto.User;
import com.security.dto.Vendor;
import com.security.entity.Admin;

public interface AdminService {
	
	Admin saveAdmin(Admin admin);
	
	List<Admin> getAllAdmins();
	
	Admin getOneAdminById(String id);
	
	String deleteAdminById(String id);
	
	Optional<Admin> getByUsername(String username);
	
	List<Vendor> getAllVendors();
	
	ResponseEntity<?> saveVendor(Vendor vendorDto);
	
	Admin getAdminFromToken();
	
	
	Vendor findByEmail(String email);
	
	
	ResponseEntity<?> saveProduct(ProductDto productDto);
	Optional<ProductDto> findByProductId(String productId);
	List<ProductDto> getAll();
	ResponseEntity<?> update(String id, ProductDto productDto);
	ProductDto delete(String id);

	User findByUserName(String userName);
	


}
