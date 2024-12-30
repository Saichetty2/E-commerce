package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.dto.Vendor;
import com.security.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	
	Optional< Admin> findByUserName(String username);
	
	
	
	
}

