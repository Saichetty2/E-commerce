package com.security.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
	
	
    @Id
    private String adminId;
    
    private String adminName;
    
    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private boolean vendorsEnanble;

    @Column(nullable = false)
    private String role; 
    
}
