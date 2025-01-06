package com.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.cart.dtos.UserDto;

@FeignClient(value = "SECURITY-SERVICE", path = "/admin")
public interface SecurityClient {

		
	

}
