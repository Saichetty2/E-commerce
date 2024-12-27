package com.vendorService.service;

import java.util.List;
import java.util.Optional;

import com.vendorService.dto.VendorDto;
import com.vendorService.entity.Vendor;

public interface VendorService {
	
	Vendor saveVendor(Vendor vendor);
	
	VendorDto getById(String id);
	
	List<VendorDto> getAllVendors();
	
	Vendor findByEmail(String email);
	
	Optional<Vendor> findByGstn(String gstn);
	
	List<VendorDto> findByAdmin(Long adminId);
	
	List<VendorDto> findByAddedBy(String adminName);

}
