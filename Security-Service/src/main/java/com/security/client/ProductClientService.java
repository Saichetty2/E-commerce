package com.security.client;

import java.util.List;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.security.dto.ProductDto;

@FeignClient(value = "PRODUCT-SERVICE", path = "/product")
public interface ProductClientService {

	
	@PostMapping("/save-product")
	public ResponseEntity<?> saveProduct(@RequestBody ProductDto product);
	
	@GetMapping("/find/{id}")
	public Optional<ProductDto>  getById(@PathVariable long id);
	
	@PutMapping("/update-product/{id}")
	public ProductDto updateProduct(@PathVariable long id, @RequestBody ProductDto product);
	
	@GetMapping("/find-products")
	public List<ProductDto> getAllProducts();
	
	
}
