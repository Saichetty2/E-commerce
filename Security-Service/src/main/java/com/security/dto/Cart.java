package com.security.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	
    private long cartId;
    
    private LocalDateTime createdDate;
    
    private String userId;
    
    private Double totalAmount;

    //mapping cart-items
//    private List<CartItemDto> items=new ArrayList<>();
    
    private List<CartItem> items=new ArrayList<>();

}
