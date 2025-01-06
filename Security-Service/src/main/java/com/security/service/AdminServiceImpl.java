package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.security.client.CartClientService;
import com.security.client.ProductClientService;
import com.security.client.UserClientService;
import com.security.client.VendorClientService;
import com.security.dto.Cart;
import com.security.dto.CartItem;
import com.security.dto.ProductDto;
import com.security.dto.User;
import com.security.dto.Vendor;
import com.security.entity.Admin;
import com.security.repository.AdminRepository;
import com.security.security.JwtAuthenticationFilter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private VendorClientService vendorClientService;

	@Autowired
	private ProductClientService productClientService;

	@Autowired
	private UserClientService userClientService;
	
	@Autowired
	private CartClientService cartClientService;

//    this instance having username from the token
//    successfully completed this on 13.12.2024 0244pm
	JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();

//  ADD ADMIN ONLY DONE BY THE SUPER_ADMIN
	@Override
	public Admin saveAdmin(Admin admin) {
		String prefix = "ROLE_";
		String adminId = UUID.randomUUID().toString();
		admin.setAdminId(adminId);
		admin.setRole(prefix + admin.getRole().toUpperCase());
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		Admin savedAdmin = adminRepository.save(admin);
		return savedAdmin;
	}

//	DONE ONLY BY SUPER_ADMIN
	@Override
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

//	DONE ONLY BY SUPER_ADMIN
	@Override
	public Admin getOneAdminById(String id) {
		Optional<Admin> admin = adminRepository.findById(id);
		return admin.orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id));
	}

//	get admin from token
	public Admin getAdminFromToken() {
		return adminRepository.findByUserName(jwtAuthenticationFilter.getUsername()).get();
	}

//	get vendor from token
	public Vendor getVendorFromToken() {
		Vendor vendorByEmail = vendorClientService.getByEmail(jwtAuthenticationFilter.getUsername());
		System.out.println("this is vendor details from security service: " + vendorByEmail);
		return vendorByEmail;
	}

// get user from token
	public User getUserFromToken() {
		System.out.println("This is get user from token method in security service: ");
		return userClientService.findByUserNameOrEmail(jwtAuthenticationFilter.getUsername());
	}

//    getting username from the token
	public Optional<Admin> getByUserNameFromToken(String userName) {
		return adminRepository.findByUserName(jwtAuthenticationFilter.getUsername());
	}

//	DONE ONLY BY SUPER_ADMIN
	@Override
	public String deleteAdminById(String id) {
		Optional<Admin> admin = adminRepository.findById(id);
		if (admin.isPresent()) {
			adminRepository.deleteById(id);
			return "Admin deleted successfully.";
		} else {
			throw new RuntimeException("Admin not found with ID: " + id);
		}
	}

//	DONE ONLY BY SUPER_ADMIN
	@Override
	public List<Vendor> getAllVendors() {
		List<Vendor> allVendors = vendorClientService.getAllVendors();
		return allVendors;
	}

	@Override
	public ResponseEntity<?> saveVendor(Vendor vendorDto) {

		vendorDto.setAddedBy(getAdminFromToken().getAdminName());
		vendorDto.setAdminId(getAdminFromToken().getAdminId());
		vendorDto.setRole("ROLE_VENDOR");

		System.out.println("666666666666666666: " + vendorDto.getAddedBy());
		System.out.println("666666666666666666: " + vendorDto.getAdminId());

		ResponseEntity<?> savedVendor = vendorClientService.saveVendor(vendorDto);

		return savedVendor;
	}

	@Override
	public Optional<Admin> getByUsername(String username) {
		return adminRepository.findByUserName(username);
	}

	@Override
	public Vendor findByEmail(String email) {
		return vendorClientService.getByEmail(email);
	}

	@Override
	public ResponseEntity<?> saveProduct(ProductDto productDto) {

//		automatically its adding vendor unique id to the product while saving the product.
//		here, i want to add get vendor, get admin user details from token and pass them to respective loop.

//		using terinanry operator to check null values before saving the operation

		String userName = null;
		String email = null;
		if (userName == null) {

			email = getVendorFromToken() != null ? getVendorFromToken().getEmail() : null;
		}

		if (email == null) {
			userName = getAdminFromToken() != null ? getAdminFromToken().getUserName() : null;
		}

		if (email != null && userName == null) {
			productDto.setVendorId(email);

		} else if (userName != null && email == null) {
			productDto.setVendorId(userName);

		} else {
			return ResponseEntity.badRequest().body("Invalid token: Could not determine vendor or admin.");
		}

		ResponseEntity<?> savedProduct = productClientService.saveProduct(productDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct.getBody());
	}

//	finding all products
	@Override
	public List<ProductDto> getAll() {
		List<ProductDto> allProducts = productClientService.getAllProducts();

		allProducts.forEach(prod -> prod.setTotalProducts(allProducts.size()));

		return allProducts;
	}

	@Override
	public ResponseEntity<?> update(String id, ProductDto productDto) {

		String userName = null;
		String email = null;

		if (userName == null) {

			email = getVendorFromToken() != null ? getVendorFromToken().getEmail() : null;
		}

		if (email == null) {
			userName = getAdminFromToken() != null ? getAdminFromToken().getUserName() : null;
		}

		Optional<ProductDto> existingProduct = productClientService.getById(id);

		if (!existingProduct.isPresent()) {
			return ResponseEntity.badRequest().body("Product not found");
		}

		ProductDto existProduct = existingProduct.get();

		if (existProduct.getVendorId().equals(email) || existProduct.getVendorId().equals(userName)) {
			System.out.println("This is condition loop for Vendor and Admin login");
			ProductDto updatedProduct = productClientService.updateProduct(id, productDto);

			return ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);
		}
//		     else if() {
//			      ProductDto updatedProduct = productClientService.updateProduct(id, productDto);
		System.out.println("Checking username: " + userName);
//		    }
		System.out.println("This sysout is without loop, error message");

		return ResponseEntity.badRequest().body("Sorry, you can't update this Product. details may please verify...!");

	}

	@Override
	public Optional<ProductDto> findByProductId(String productId) {

		Optional<ProductDto> findById = productClientService.getById(productId);

		return findById;
	}

	@Override
	public ProductDto delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
//	finding username for the user service
	@Override
	public User findUserByUserName() {
		User findByUserNameOrEmail = userClientService.findByUserNameOrEmail(getUserFromToken().getEmail());
		System.out.println("This is now created method for user email: "+ findByUserNameOrEmail);
		return findByUserNameOrEmail;
	}

	@Override
	public ResponseEntity<Cart> addItemToCart(String userId, String productId, CartItem request) {

		if (findUserByUserName()!=null) {
			userId=findUserByUserName().getUserId();
		             
		}
	
		System.out.println(" Newly created addItem to cart method: "+ findUserByUserName().getUserId()+ " and user id is: "+ userId);
		return cartClientService.addItemToCart(userId, productId, request);
	}

	
	
	
	
	

}
