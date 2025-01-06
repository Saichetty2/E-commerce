package com.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cart.dtos.ProductDto;


@FeignClient(value  = "PRODUCT-SERVICE", path = "/product")
public interface ProductClient {
    
	@GetMapping("/find/{id}")
	public ProductDto getById(@PathVariable String id);

    @GetMapping("/find-products")
    ProductDto getAllProducts();
}
