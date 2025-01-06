package com.cart.dtos;


import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
	
	
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
   
    
   
    @OneToOne(mappedBy = "userDto")
    private CartDto cartDto;

  
}
