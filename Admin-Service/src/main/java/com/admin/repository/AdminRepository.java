package com.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.admin.entity.Admin;
import java.util.Optional;



public interface AdminRepository extends JpaRepository<Admin, Long> {
	
    Admin findByUsername(String username);
}
