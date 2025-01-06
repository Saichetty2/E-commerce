package com.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.security.dto.Cart;
import com.security.dto.CartItem;

@FeignClient(value = "CART-SERVICE", path = "/carts")
public interface CartClientService {
	
	@PostMapping("/{productId}/addcart")
	public ResponseEntity<Cart> addItemToCart(@RequestParam String userId, @PathVariable String productId,@RequestBody CartItem request) ;
	
	 @DeleteMapping("/item/{cartItemId}")
	  public String deleteCartItem(@RequestParam String userId, @PathVariable long cartItemId);
		
}
