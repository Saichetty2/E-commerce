package com.product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.entity.Category;
import com.product.exceptions.ResourceNotFoundException;
import com.product.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Category saveCategory(Category category) {
        logger.info("Saving category with name: {}", category.getCatName());

        // Validate category name and convert to uppercase
        if (category.getCatName() != null) {
            category.setCatName(category.getCatName().toUpperCase());
        }

        // Save category and log the result
        Category savedCategory = categoryRepository.save(category);
        logger.info("Category saved successfully with ID: {}", savedCategory.getCategoryId());

        return savedCategory;
    }

    @Override
    public List<Category> getAllCategories() {
        logger.info("Fetching all categories");

        // Fetch all categories and log the result
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            logger.warn("No categories found in the database.");
        } else {
            logger.info("Fetched {} categories", categories.size());
        }

        return categories;
    }

    @Override
    public Category getById(String id) {
        logger.info("Fetching category with ID: {}", id);

        // Fetch category by ID and log the result
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", id);
                    return new ResourceNotFoundException("Category not found with ID: " + id);
                });

        logger.info("Fetched category: {} with ID: {}", category.getCatName(), id);
        return category;
    }
}
