package com.cart.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cart.entities.CartItem;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
	
    private long cartId;
    
    private LocalDateTime createdDate;
    
    private String userId;
    
    private Double totalAmount;

    //mapping cart-items
//    private List<CartItemDto> items=new ArrayList<>();
    
    private List<CartItem> items=new ArrayList<>();


}