package com.product.dto;

import java.util.List;
import com.product.entity.Product;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private String categoryId;
	private String catName;
	
	@OneToMany(mappedBy = "category")
	private List<Product> products;

}
