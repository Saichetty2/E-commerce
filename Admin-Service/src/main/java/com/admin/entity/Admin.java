package com.admin.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
	
	
    @Id
    private String adminId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    
    
    private String email;

    @Column(nullable = false)
    private String role; 
    
    
    
    
    
    
}
