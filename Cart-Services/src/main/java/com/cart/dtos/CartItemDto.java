package com.cart.dtos;

import com.cart.entities.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

	private long cartItemId;
	private String productId;
	private String productTitle;
    private int quantity;
    private double productPrice;
    

    
}