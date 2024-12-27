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
	Product getByProductId(Long id);
	
//	update
	Product updateById(long id, Product product);
	
//	delete
	void deleteProduct(long id);
	
	List<Product> getProductsByCatId(String catId);

	

}
