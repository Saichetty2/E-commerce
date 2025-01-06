package com.product.service;

import java.util.List;

import com.product.entity.Category;
import com.product.entity.Product;

public interface CategoryService  {
	
	Category saveCategory(Category category);
	
	List<Category>	 getAllCategories();
	
	Category getById(String id);
	
	
	
	

}
