package com.security.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.ProductDto;
import com.security.dto.User;
import com.security.dto.Vendor;
import com.security.entity.Admin;
import com.security.service.AdminService;
import com.security.service.AdminServiceImpl;

import jakarta.ws.rs.POST;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    AdminServiceImpl adminServiceImpl = new AdminServiceImpl();

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/save-admin")
    public ResponseEntity<Admin> saveAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.saveAdmin(admin);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/get-admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    // Get one admin by ID
    @GetMapping("/get-admin/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable String id) {
        Admin admin = adminService.getOneAdminById(id);
        return new ResponseEntity<>(admin, HttpStatus.ACCEPTED);
    }
    
    
    @GetMapping("/get-admin/u/{username}")
    public Optional<Admin> getAdminByUsername(@PathVariable String username) {
    	return adminService.getByUsername(username);
    }
    
    @GetMapping("/v/{email}")
    public Vendor getVendorByEmail(@PathVariable String email){
    	return adminService.findByEmail(email);
    }
    

    // Delete an admin by ID
    @DeleteMapping("/delete-admin/{id}")
    public ResponseEntity<String> deleteAdminById(@PathVariable String id) {
        String response = adminService.deleteAdminById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/home")
    public String home() {
    	return "Hello welcome to Admin home page ";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-vendors")
    public List<Vendor> getAllVendors(){
    	System.out.println("Get all vendors called in security service $$$$$$");
    	return adminService.getAllVendors();
    }

    
   @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save-ven")
    public ResponseEntity<?> saveVendor(@RequestBody Vendor vendorDto) {
    	if(adminService.getAdminFromToken().isVendorsEnanble()==true) {
    		ResponseEntity<?> saveVendor = adminService.saveVendor(vendorDto);
    		return  saveVendor;
    	}
    	return ResponseEntity.ok(" Sorry, you dont have permission to Create vendor, please consult your super admin");
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDOR')")
    @PostMapping("/save/product")
    public ResponseEntity<?> saveProducts(@RequestBody ProductDto productDto){
    	ResponseEntity<?> savedProduct = adminService.saveProduct(productDto);
		return savedProduct;
    }
    
    
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDOR')")
    @PutMapping("/update/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto){
		return adminService.update(productId, productDto);
    }
    
    
//    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDOR') or hasRole('USER')")
    @GetMapping("/getall-products")
    public List<ProductDto> getAllproducts(){
    	return adminService.getAll();
    }
    
//  Sending username of the user to the user service from security service
//    @GetMapping("/user/username")
//    public String findUserName() {
//    	return adminServiceImpl.getUserNameFromTheToken();
//    }
    
    @GetMapping("/get/user")
    public User findTheUserEmail() {
    	return adminServiceImpl.findByEmail();
    }
    
   
}

