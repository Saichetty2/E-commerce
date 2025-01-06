package com.cart.service;

import com.cart.dtos.CartDto;
import com.cart.dtos.CartItemDto;
import com.cart.dtos.UserDto;
import com.cart.entities.Cart;
import com.cart.entities.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartService {

    //add item to cart:
    //case:1 cart for the user is not available: we will create the cart and add the item
    //cart available:add the item to the cart

//	add product to the cart based on user id
//    CartDto addItemToCart(String userId,String productId, CartItemDto request);
	 CartDto addItemToCart(String userId, String productId, CartItemDto request);
	
	
//    find all cart
    List<Cart> getAllCart();
    
    void deleteSingleItem(long id);
    
    CartDto getCartByUserId(String userId);
    
//  Get cart by cart id
    Cart getCartByCartId(long cartId);
    
//    void deleteItem(long cartItemId);
    
    CartDto deleteCartItemForUser(String userId, long cartItemId);
    
    void deleteCart(long cartId);
    
    CartDto reduceCartItemQuantityForUser(String userId, long cartItemId);
    
    //Remove item from cart
//    void removeItemFromCart(String userId,String cartItemId);
//
//    //remove all item from the cart
//    void clearCart(String userId);

    

}
