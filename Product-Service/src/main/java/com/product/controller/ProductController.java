package com.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.product.entity.Category;
import com.product.entity.Product;
import com.product.service.CategoryService;
import com.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	
	@Autowired
	private ProductService productService;
	
	@Autowired 
	private CategoryService categoryService;
	
	
//to save the products
	@PostMapping("/save-product")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product){
		
//		Category categoryArrivedHere = categoryService.getById(product.getCategoryId());
		
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%##################@@@@@@@@@@@@@@@ "
//				+ "categoryArrivedHere Saikumar " + categoryArrivedHere);
		
//		product.setCategory(categoryArrivedHere);
		
		Product savedProduct = productService.saveProduct(product);
		
//		return new ResponseEntity<>(HttpStatus.CREATED);
		return  ResponseEntity.ok(savedProduct);

	
	}
	
	
//to find all products
	@GetMapping("/find-products")
	public List<Product> getAllProducts(){
		List<Product> allProducts = productService.getAllProducts();
		System.out.println(allProducts.size());
		return  allProducts;
	}
	
	
//	to find the product by id
	@GetMapping("/find/{id}")
	public Product getById(@PathVariable long id) {
		Product productId = productService.getByProductId(id);
		
		return productId;
	}
	
//	to update the product
	@PutMapping("/update-product/{id}")
	public Product updateProduct(@PathVariable long id, @RequestBody Product product) {
		return productService.updateById(id, product);
	}
	
//	 to delete the product

	@DeleteMapping("/delete-product{id}")
	public void deleteProduct(@PathVariable Integer id) {
		productService.deleteProduct(id);
	}
	
	@GetMapping("/catid/{catId}")
	public List<Product> getProductsByCateforyId(@PathVariable String catId){
		return productService.getProductsByCatId(catId);
		
	}
	
}
