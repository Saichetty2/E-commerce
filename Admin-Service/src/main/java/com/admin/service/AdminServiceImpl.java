package com.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.admin.entity.Admin;
import com.admin.repository.AdminRepository;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    
    
    
    @Override
    public Admin saveAdmin(Admin admin) {
    	String prefix = "ROLE_";
    	admin.setRole(prefix+admin.getRole().toUpperCase());
    	admin.setPassword(passwordEncoder.encode(admin.getPassword()));
    	Admin savedAdmin = adminRepository.save(admin);
        return savedAdmin;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getOneAdminById(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.orElseThrow(() -> new RuntimeException("Admin not found with ID: " + id));
    }

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
    
    
    public Admin getByUsername(String username) {
    	return adminRepository.findByUsername(username);
    }

   
}
