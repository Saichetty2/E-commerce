package com.vendorService.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendorService.dto.VendorDto;
import com.vendorService.entity.Vendor;
import com.vendorService.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {
	
	
	
	@Autowired
	private VendorService vendorService;
	
	
	@GetMapping("/home")
	public String home() {
		return "Welcome to home";
	}
	
	
	@PostMapping("/save-vendor")
	public ResponseEntity<Vendor> saveVendor(@RequestBody Vendor vendor){
//		need to create a method for adding the adminId field from the another ms
		
		Vendor savedVendor = vendorService.saveVendor(vendor);
		
		return ResponseEntity.ok(savedVendor);
	}
	
	@GetMapping("/get-vendor/{id}")
	public ResponseEntity<VendorDto>  getVendorById(@PathVariable String id) {
		VendorDto byId = vendorService.getById(id);
		return ResponseEntity.ok(byId);
	}
	
	@GetMapping("/get-allvendors")
	public ResponseEntity<List<VendorDto>> getAllVendors(){
		List<VendorDto> allVendors = vendorService.getAllVendors();
		return ResponseEntity.ok(allVendors);
		
	}
	
	
	@GetMapping("/get-email/{email}")
	public Vendor getByEmail(@PathVariable String email) {
		return vendorService.findByEmail(email);
	}
	
	
	@GetMapping("/get-admin/{admin}")
	public List<VendorDto> getByAdmin(@PathVariable String admin){
		List<VendorDto> findByAdmin = vendorService.findByAdmin(admin);
		return findByAdmin;
	}
		
	
		@GetMapping("/get-gstn/{gstn}")
		public Optional<Vendor> getyGstn(@PathVariable String gstn) {
			Optional<Vendor> findByGstn = vendorService.findByGstn(gstn);
			return findByGstn;
		}
		
		
		@GetMapping("/added/{name}")
		public List<VendorDto> getByAdminName(@PathVariable String name){
			List<VendorDto> findByAdmin = vendorService.findByAddedBy(name);
			return findByAdmin;
		}
	

}
