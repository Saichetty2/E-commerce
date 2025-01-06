package com.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cart.entities.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
	
	
    Cart findByUserId(String userId);
    Cart findByCartId(long id);
    
}
