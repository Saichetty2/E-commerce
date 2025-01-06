package com.user.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "cart-service", url = "http://localhost:8081")
public interface CartClient {
	
	
	
	

}
