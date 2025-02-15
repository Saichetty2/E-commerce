package com.product.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	
	List<Product> findByCatId (String id);
	
	Optional<Product> findByProductId(String productId);
	
	
}
