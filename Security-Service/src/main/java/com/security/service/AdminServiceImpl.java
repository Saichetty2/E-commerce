package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.security.client.ProductClientService;
import com.security.client.UserClientService;
import com.security.client.VendorClientService;
import com.security.dto.ProductDto;
import com.security.dto.User;
import com.security.dto.Vendor;
import com.security.entity.Admin;
import com.security.repository.AdminRepository;
import com.security.security.JwtAuthenticationFilter;
import java.util.List;
import java.util.Optional;

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

//    this instance having username from the token
//    successfully completed this on 13.12.2024 0244pm
	JwtAuthenticationFilter jwtTokenProvider = new JwtAuthenticationFilter();

//    ADD ADMIN ONLY DONE BY THE SUPER_ADMIN
	@Override
	public Admin saveAdmin(Admin admin) {
		String prefix = "ROLE_";
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
	public Admin getOneAdminById(Long id) {
		Optional<Admin> admin = adminRepository.findById(id);
		return admin.orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id));
	}

	public Admin getAdminFromToken() {
		return adminRepository.findByUserName(jwtTokenProvider.getUsername()).get();
	}

	public Vendor getVendorFromToken() {
		return vendorClientService.getByEmail(jwtTokenProvider.getUsername());
	}
	
//	creating method for the controller to take access form the feign client
	public String getUserNameFromTheToken() {
		return jwtTokenProvider.getUsername();
		
	}

//    getting username from the token
	public Optional<Admin> getByUserNameFromToken(String userName) {
		return adminRepository.findByUserName(jwtTokenProvider.getUsername());
	}

//	DONE ONLY BY SUPER_ADMIN
	@Override
	public String deleteAdminById(Long id) {
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

		vendorDto.setAddedBy(getAdminFromToken().getName());
		vendorDto.setAdminId(getAdminFromToken().getId());
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
		String email = getVendorFromToken() != null ? getVendorFromToken().getEmail() : null;
		String userName = getAdminFromToken() != null ? getAdminFromToken().getUserName() : null;

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

	@Override
	public List<ProductDto> getAll() {
		List<ProductDto> allProducts = productClientService.getAllProducts();
		return allProducts;
	}

	@Override
	public ResponseEntity<?> update(long id, ProductDto productDto) {

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
	public Optional<ProductDto> findByProductId(Long productId) {

		Optional<ProductDto> findById = productClientService.getById(productId);

		return findById;
	}

	@Override
	public ProductDto delete(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUserName(String userName) {
		User findByUserNameOrEmail = userClientService.findByUserNameOrEmail(userName);
		return findByUserNameOrEmail;
	}
	
	
	public User findByEmail() {
		return userClientService.findByEmail(getUserNameFromTheToken());
	}

}
