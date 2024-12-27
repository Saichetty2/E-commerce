package com.vendorService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vendorService.dto.VendorDto;
import com.vendorService.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, String> {

	Vendor findByEmail(String email);
	Vendor findByGstn(String gstn);
	List<Vendor> findByAdminId(Long id);
	List<Vendor> findByAddedBy(String name);
	
	
}
