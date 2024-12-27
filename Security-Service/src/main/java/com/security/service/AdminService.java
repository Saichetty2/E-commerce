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
	
	Admin getOneAdminById(Long id);
	
	String deleteAdminById(Long id);
	
	Optional<Admin> getByUsername(String username);
	
	List<Vendor> getAllVendors();
	
	ResponseEntity<?> saveVendor(Vendor vendorDto);
	
	Admin getAdminFromToken();
	
	
	Vendor findByEmail(String email);
	
	
	ResponseEntity<?> saveProduct(ProductDto productDto);
	Optional<ProductDto> findByProductId(Long productId);
	List<ProductDto> getAll();
	ResponseEntity<?> update(long id, ProductDto productDto);
	ProductDto delete(long id);

	User findByUserName(String userName);
	


}
