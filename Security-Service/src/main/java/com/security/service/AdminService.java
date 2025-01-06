package com.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.security.dto.Cart;
import com.security.dto.CartItem;
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

	User findUserByUserName();

	ResponseEntity<Cart> addItemToCart(String userId,String productId,CartItem request);
}
