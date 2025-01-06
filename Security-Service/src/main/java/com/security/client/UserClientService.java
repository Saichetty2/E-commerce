package com.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.security.dto.User;



@FeignClient(value = "USER-SERVICE", path = "/user")
public interface UserClientService {
	
	
	@GetMapping("/get-ue/{userNameOrEmail}")
    public User findByUserNameOrEmail(@PathVariable String userNameOrEmail);
	
	
	
	 @GetMapping("/get-e/{email}")
	 public User findByEmail(@PathVariable String email) ;
	
	
}
