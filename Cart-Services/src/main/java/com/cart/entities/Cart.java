package com.cart.entities;

import jakarta.persistence.*;
import lombok.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cart.dtos.CartItemDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;
    
    @JsonBackReference
    private String userId;
    
    private Double totalCartValue;

    //mapping cart-items
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    private List<CartItem> items=new ArrayList<>();
    
    private LocalDateTime cartCreatedTime;

    public void addItem(CartItem item) {
    	this.items.add(item);
        item.setCart(this);
        

    }
}
