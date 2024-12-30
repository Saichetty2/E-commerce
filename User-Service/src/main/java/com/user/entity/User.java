package com.user.entity;

import org.hibernate.annotations.Collate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_micro")
public class User {
    
    @Id
    private String userId;
    
    @Size(min = 3,max = 30,message = "Invalid name !!")
    private String firstName;
    
    @Size(min = 3,max = 30,message = "Invalid name !!")
    private String lastName;
    
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid user email !!")
    @NotBlank(message = "Email is required !!")
    private String email;
    
    @NotBlank(message = "Password is required !!")
    private String password;
    
    @Transient
    private String password2;
    
    private String phoneNumber;
    
    @Column(unique = true)
    private String userName;
    
    private String role;


    
    
//    This will be taken from the feign client by server
//    @OneToOne(mappedBy = "user", cascade =  {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
//    private Cart cart;

}

