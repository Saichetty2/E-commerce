package com.product.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    
//     i'm commenting this field at 0500pm 
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id") // Foreign key column name
    private Category category;
    
    private String catId;

    @PrePersist
    @PreUpdate
    public void setGstAndStock() {
        this.addedDate = LocalDateTime.now(); // Fixed the syntax
        this.productPrice = actualPrice + ((actualPrice * sgst / 100) + (actualPrice * cgst / 100));

        // Simplified stock and live logic
        this.stock = this.quantity > 0;
        this.live = this.quantity > 0;
    }
}
