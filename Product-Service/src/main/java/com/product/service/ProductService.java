package com.product.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.product.entity.Product;


public interface ProductService {
	
//	save
	Product saveProduct (Product product);
	
//	getAll
	List<Product> getAllProducts();
	
//	getById
	Product getByProductId(String id);
	
//	update
	Product updateById(String id, Product product);
	

	
	List<Product> getProductsByCatId(String catId);

	void deleteProduct(String id);

	

}
