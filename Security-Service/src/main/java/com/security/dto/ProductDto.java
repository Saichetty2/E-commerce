package com.security.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	
	
	private long productId;

    private String productTitle;
    private String description;
    private double actualPrice;
    private int discountedPrice;
    private LocalDateTime addedDate;
    private int sgst;
    private int cgst;
    private int quantity;
    private boolean live;
    private boolean stock;
    private double productPrice;
    private String vendorId;
    private String catId;

}