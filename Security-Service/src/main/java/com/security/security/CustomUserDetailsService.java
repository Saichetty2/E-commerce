package com.security.security;

import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.security.client.UserClientService;
import com.security.client.VendorClientService;
import com.security.dto.Vendor;
import com.security.entity.Admin;
import com.security.service.AdminServiceImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private VendorClientService vendorClientService;
    
    @Autowired
    private UserClientService userClientService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	
    	
        // Attempt to load user as Admin
        Admin admin = adminService.getByUsername(username).orElse(null);
        
       
        if (admin != null ) {
        	 System.out.println("Admin loop working %%%%%%%%%%%%%: "+ username);
            Collection<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority(admin.getRole()) // Example: ROLE_ADMIN
            );
            return new User(admin.getUserName(), admin.getPassword(), authorities);
        }

        
        // Attempt to load user as Vendor
        Vendor vendor = vendorClientService.getByEmail(username);
        if (vendor != null) {
        	
        	System.out.println("Vendor loop working %%%%%%%%%%%%%: "+ username);
        	System.out.println("The result of the vendor: "+ vendor);
            Collection<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority(vendor.getRole()) // Example: ROLE_VENDOR
            );
            return new User(vendor.getEmail(), vendor.getPassword(), authorities);
        }
        
     // Attempt to load user as user
       com.security.dto.User user = userClientService.findByUserNameOrEmail(username);
       
        if(user!=null) {
        	System.out.println("User loop working %%%%%%%%%%%%%: "+ username);
        	System.out.println("The result of the user: "+ user);
        	 Collection<GrantedAuthority> authorities = Collections.singleton(
                     new SimpleGrantedAuthority(user.getRole()) // Example: ROLE_VENDOR
                 );
                 return new User(user.getEmail(), user.getPassword(), authorities);
             }
        
        
        
        // User not found
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}

	
//	
//	@Autowired
//	private AdminServiceImpl adminService;
//
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		
//	    Admin admin = adminService.getByUsername(username)
//	            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//	    // Wrap the single authority in a collection
//	    Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(admin.getRole()));
//	    
//
//	    System.out.println("$$$$$$$$$$$$$$$$$$$$$ name: "+  admin.getName());
//	    
//	    return new User(
//	            admin.getUserName(),
//	            admin.getPassword(),
//	            authorities 
//	    );
//	}
//
//}

