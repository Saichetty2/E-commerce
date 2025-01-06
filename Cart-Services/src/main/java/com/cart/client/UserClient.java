package com.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cart.dtos.UserDto;

//old way
//@FeignClient(value = "USER-SERVICE", url = "http://localhost:8083/user")

//new way
@FeignClient(value = "USER-SERVICE", path = "/user")
public interface UserClient {
    
	
    @GetMapping("/find/{userId}")
    UserDto getUserById(@PathVariable String userId);
    
    @GetMapping("/get-ue/{userNameOrEmail}")
    UserDto findByUserNameOrEmail(@PathVariable String userNameOrEmail);
    
    
}
