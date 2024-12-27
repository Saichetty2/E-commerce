package com.vendorService.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.vendorService.dto.VendorDto;
import com.vendorService.entity.Vendor;
import com.vendorService.exceptions.ResourceNotFoundException;
import com.vendorService.repository.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public Vendor saveVendor(Vendor vendor) {
		vendor.setId();
		vendor.setPassword(passwordEncoder.encode(vendor.getPassword()));
		
		Vendor savedVendor = vendorRepository.save(vendor);
		return savedVendor;
	}

	@Override
	public VendorDto getById(String id) {
		Vendor vendor = vendorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+ id));
		VendorDto vendorDto= new VendorDto();
		vendorDto.setFirmName(vendor.getFirmName());
		vendorDto.setEmail(vendor.getEmail());
		
		return  vendorDto;
		
	
	}
	
	
	

	@Override
	public List<VendorDto> getAllVendors() {
		List<Vendor> findAll = vendorRepository.findAll();
		
		 if (findAll.isEmpty()) {
	        throw new ResourceNotFoundException("Vendor not found in the data");
	    }

	   
	    return findAll.stream()
                .map(vendor -> {
                    VendorDto vendorDto = new VendorDto();
                    
                    vendorDto.setId(vendor.getId());  
                    vendorDto.setFirmName(vendor.getFirmName());  
                    vendorDto.setType(vendor.getType());  
                    vendorDto.setGstn(vendor.getGstn());  
                    vendorDto.setAuthPerson(vendor.getAuthPerson()); 
                    vendorDto.setEmail(vendor.getEmail()); 
                    vendorDto.setAdminId(vendor.getAdminId()); 
                    vendorDto.setAddedBy(vendor.getAddedBy());
                    
                    return vendorDto;  
                })
                .collect(Collectors.toList());
	}


//	@Override
//	public Optional<VendorDto> findByEmail(String email) {
//		
//	    // Fetch vendor by email or throw exception if not found
//	    Vendor vendor = vendorRepository.findByEmail(email)
//	        .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with email: " + email));
//	    
//	    // Convert Vendor to VendorDto and return wrapped in Optional
//	    return convertToVendorDto(vendor);
//	}
	
	
	public Vendor findByEmail(String email) {
	    // Fetch the Vendor entity using the repository
	    Vendor vendor = vendorRepository.findByEmail(email);

	    // Check if vendor is null and return an empty Optional
//	    if (vendor == null) {
//	        return Optional.empty();
//	    }

	    
	    // Map the Vendor entity to VendorDto
//	    VendorDto vendorDto = new VendorDto();
//	    vendorDto.setAddedBy(vendor.getAddedBy());
//	    vendorDto.setAdminId(vendor.getAdminId());
//	    vendorDto.setAuthPerson(vendor.getAuthPerson());
//	    vendorDto.setEmail(vendor.getEmail());
//	    vendorDto.setFirmName(vendor.getFirmName());
//	    vendorDto.setGstn(vendor.getGstn());
//	    vendorDto.setId(vendor.getId());
//	    vendorDto.setType(vendor.getType());
//	    vendorDto.setRole(vendor.getRole());
	    // Return the mapped VendorDto wrapped in an Optional
//	    return Optional.of(vendorDto);
//	    return vendorDto;
	    return vendor;
	}



	public Optional<VendorDto> convertToVendorDto(Vendor vendor) {
	    if (vendor == null) {
	        return Optional.empty(); // Safeguard against null values
	    }
	    
	    // Map Vendor fields to VendorDto fields
	    VendorDto vendorDto = new VendorDto();
	    vendorDto.setId(vendor.getId());
	    vendorDto.setFirmName(vendor.getFirmName());
	    vendorDto.setAuthPerson(vendor.getAuthPerson());
	    vendorDto.setEmail(vendor.getEmail());
	    vendorDto.setGstn(vendor.getGstn());
	    vendorDto.setType(vendor.getType());
	    vendorDto.setAdminId(vendor.getAdminId());
	    vendorDto.setAddedBy(vendor.getAddedBy());
	    
	    return Optional.of(vendorDto);
	}

//	@Override
//	public  Optional<VendorDto> findByGstn(String gstn) {
//		vendorRepository.findByGstn(gstn).orElseThrow(() -> new ResourceNotFoundException("Vendor with this gst---"+gstn+"----not found"));
//		
//		
//		return new VendorDto(vendor.getId(), vendor.getAdminId(),vendor.getAuthPerson(),vendor.getEmail(), vendor.getFirmName(),vendor.getGstn(),vendor.getType(),vendor.getAddedBy());
//	}
//
//	@Override
//	public VendorDTO findByAdmin(Long adminId) {
//	Vendor vendor =	vendorRepository.findByAdminId(adminId).orElseThrow(() -> new ResourceNotFoundException("Vendor with this adminid---"+adminId+"----not found"));
//		return new VendorDTO(vendor.getId(), vendor.getAdminId(),vendor.getAuthPerson(),vendor.getEmail(), vendor.getFirmName(),vendor.getGstn(),vendor.getType(),vendor.getAddedBy());
//	}

	@Override
	public List<VendorDto> findByAddedBy(String adminName) {
		List<Vendor> findByAddedBy = vendorRepository.findByAddedBy(adminName);
		return findByAddedBy.stream()
                .map(vendor -> {
                    VendorDto vendorDto = new VendorDto();
                    
                    vendorDto.setId(vendor.getId());  
                    vendorDto.setFirmName(vendor.getFirmName());  
                    vendorDto.setType(vendor.getType());  
                    vendorDto.setGstn(vendor.getGstn());  
                    vendorDto.setAuthPerson(vendor.getAuthPerson()); 
                    vendorDto.setEmail(vendor.getEmail()); 
                    vendorDto.setAdminId(vendor.getAdminId()); 
                    vendorDto.setAddedBy(vendor.getAddedBy());
                    
                    return vendorDto;  
                })
                .collect(Collectors.toList());
	}

	@Override
	public Optional<Vendor> findByGstn(String gstn) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<VendorDto> findByAdmin(Long adminId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
