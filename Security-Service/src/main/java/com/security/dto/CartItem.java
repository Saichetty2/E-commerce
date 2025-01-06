package com.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
	
	    private long cartItemId;
	    private String productId;
	    private String productTitle;
	    private int quantity;
	    private double productPrice;
	   

}
