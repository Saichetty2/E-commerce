package com.security.client;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.security.dto.Vendor;

@FeignClient(name = "VENDOR-SERVICE" , url  = "http://localhost:8084/vendor")
public interface VendorClientService {

	
	
	@GetMapping("/get-allvendors")
	public List<Vendor> getAllVendors();
	
	@PostMapping("/save-vendor")
	public ResponseEntity<?> saveVendor(@RequestBody Vendor vendorDto);
	
	@GetMapping("/get-email/{email}")
	public Vendor getByEmail(@PathVariable String email);
	
}
