package com.cart.controllers;

import com.cart.dtos.CartDto;
import com.cart.dtos.CartItemDto;
import com.cart.dtos.UserDto;
import com.cart.entities.Cart;
import com.cart.service.CartService;
import com.cart.service.CartServiceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
	
	 private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	private CartService cartService;
	
	

	//adding products to the user's cart based on userId and productIs
	@PostMapping("/{productId}/addcart")
	public ResponseEntity<CartDto> addItemToCart(String userId, @PathVariable String productId,
			@RequestBody CartItemDto request) {
	System.out.println("This is Cart controller addCart method: ");

//	 userId = "62d013a9-85b0-4b22-9f39-bac26a88f44d";
	
		CartDto cartDto = cartService.addItemToCart( userId, productId, request);

		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}
	


	
//	get Cart by cart id....... i think this is not nessessary
	@GetMapping("/id/{cartId}")
	public ResponseEntity<Cart> getCartById(@PathVariable long cartId) {
		Cart cartDto = cartService.getCartByCartId(cartId);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}
	
	
	
//  find all carts 
	@GetMapping("/findall")
	public ResponseEntity<List<Cart>> getAllCarts() {
		List<Cart> findAllCart = cartService.getAllCart();
		return ResponseEntity.ok(findAllCart);
	}
	

	
	
//	im getting cartbycartid but i want cartbyuserid
//	completed on 22.11.2024
//	Done verified on 02.12.2024 working fine as expected
	@GetMapping("/user-cart/{userId}") 
	public CartDto getCartByUserId(@PathVariable String userId ) {
		
		CartDto cartByUserId = cartService.getCartByUserId(userId);
	
		return cartByUserId;
		
	}
	
	
//	writing delete cart items method 12.30 pm
//	Done verified on 02.12.2024 working fine
//	 @DeleteMapping("/{userId}/item/{cartItemId}")
//	    public String deleteCartItem(@PathVariable String userId, @PathVariable long cartItemId) {
//	        cartService.deleteCartItemForUser(cartItemId);
//	        if(getCartByUserId(userId).getCartDto().getItems().isEmpty()) {
//	        	long cartId = getCartByUserId(userId).getCartDto().getCartId();
//	        	cartService.deleteCart(cartId);
//	        }
//	        return "Cart item with ID " + cartItemId + " deleted for user ID " + userId;
//	    }


	@DeleteMapping("/{userId}/item/{cartItemId}")
	public ResponseEntity<?> deleteCartItem(@PathVariable String userId, @PathVariable long cartItemId) {
	    logger.info("Deleting cart item with ID: {} for user ID: {}", cartItemId, userId);

	    // Delete the cart item and get the updated cart
	    CartDto updatedCart = cartService.deleteCartItemForUser(userId, cartItemId);

	    // Return the updated cart
	    return ResponseEntity.ok(updatedCart);
	}

	 @PutMapping("/{userId}/item/{cartItemId}/reduce")
	    public ResponseEntity<CartDto> reduceCartItemQuantity(
	            @PathVariable String userId,
	            @PathVariable long cartItemId) {
	        logger.info("Reducing quantity for cart item with ID: {} for user ID: {}", cartItemId, userId);

	        // Call the service layer to handle the business logic
	        CartDto updatedCart = cartService.reduceCartItemQuantityForUser(userId, cartItemId);

	        return ResponseEntity.ok(updatedCart);
	    }



	
	

}
