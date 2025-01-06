package com.product.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.CategoryDto;
import com.product.entity.Category;
import com.product.entity.Product;
import com.product.repository.ProductRepository;
import com.product.service.CategoryService;
import com.product.service.ProductService;

@RestController
public class CategoryController {
	
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	
	
	@PostMapping("/savecategory")
	public ResponseEntity<Category> saveCategory( @RequestBody Category category){
		
		Category savedCategory = categoryService.saveCategory(category);
		return new ResponseEntity<Category>(savedCategory, HttpStatus.ACCEPTED);
	}
	
	
	
	
//	 re wrote this code at 11.30pm 11.nov.2024
//	getting sleep
	
//	only FR details along with products have seen
//	@GetMapping("/getallcat")
	public List<CategoryDto> getAllCategories() {

		CategoryDto categoryDto = new CategoryDto();

		List<Category> allCategories = categoryService.getAllCategories();

		List<CategoryDto> allCat = null;

		return allCat = allCategories.stream().map(eachItem -> {
			categoryDto.setCategoryId(eachItem.getCategoryId());
			categoryDto.setCatName(eachItem.getCatName());

//			List<Product> findByCategoryId = productRepository.findByCategoryId(eachItem.getCategoryId());
			for (Category s : allCategories) {
//				categoryDto.setProducts(findByCategoryId);
			}
			return categoryDto;
		}).collect(Collectors.toList());

	}
	
	
	@GetMapping("/getallcat")
	public List<Category> getAllCats(){
		return categoryService.getAllCategories();
	}
	

	
	@GetMapping("/getcat/{id}")
	public CategoryDto getCatById(@PathVariable String id) {
		
		
		CategoryDto categoryDto= new CategoryDto();
		Category catById = categoryService.getById(id);
		
		categoryDto.setCategoryId(catById.getCategoryId());
		categoryDto.setCatName(catById.getCatName());
		
//		List<Product> findProductByCategoryId = productRepository.findByCategoryId(id);
		
//		categoryDto.setProducts(findProductByCategoryId);
		
		
		return categoryDto;
		
	}

}
