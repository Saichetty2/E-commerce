package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.entity.Admin;
import com.admin.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/save-admin")
    public ResponseEntity<Admin> saveAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.saveAdmin(admin);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

    // Get all admins
    @GetMapping("/get-admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    // Get one admin by ID
    @GetMapping("/get-admin/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.getOneAdminById(id);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }
    
    
    @GetMapping("/get-admin/u/{username}")
    public Admin getAdminByUsername(@PathVariable String username) {
    	return adminService.getByUsername(username);
    }

    // Delete an admin by ID
    @DeleteMapping("/delete-admin/{id}")
    public ResponseEntity<String> deleteAdminById(@PathVariable Long id) {
        String response = adminService.deleteAdminById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   
}
