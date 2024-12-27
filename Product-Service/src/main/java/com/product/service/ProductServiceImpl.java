package com.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.entity.Product;
import com.product.exceptions.ResourceNotFoundException;
import com.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        logger.info("Attempting to save product: {}", product);
        Product savedProduct = productRepository.save(product);
        logger.info("Product saved successfully with ID: {}", savedProduct.getProductId());
        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        logger.info("Fetching all products from the database");
        List<Product> findAllProducts = productRepository.findAll();
        if (findAllProducts.isEmpty()) {
            logger.warn("No products found in the database");
        } else {
            logger.info("Found {} products in the database", findAllProducts.size());
        }
        return findAllProducts;
    }

    @Override
    public Product getByProductId(Long productId) {
        logger.info("Fetching product with ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ResourceNotFoundException("Product not found with ID: " + productId);
                });
        logger.info("Product fetched successfully: {}", product);
        return product;
    }

    @Override
    public Product updateById(long id, Product updatedProduct) {
        logger.info("Updating product with ID: {}", id);
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> {
            logger.error("Product not found with ID: {}", id);
            return new RuntimeException("Product not found with ID: " + id);
        });
        logger.info("Existing product details: {}", existingProduct);

        existingProduct.setProductTitle(updatedProduct.getProductTitle());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setActualPrice(updatedProduct.getActualPrice());
        existingProduct.setDiscountedPrice(updatedProduct.getDiscountedPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setSgst(updatedProduct.getSgst());
        existingProduct.setCgst(updatedProduct.getCgst());
        existingProduct.setCatId(updatedProduct.getCatId());

        Product updated = productRepository.save(existingProduct);
        logger.info("Product updated successfully: {}", updated);
        return updated;
    }

    @Override
    public void deleteProduct(long id) {
        logger.info("Attempting to delete product with ID: {}", id);
        if (!productRepository.existsById(id)) {
            logger.error("Product not found with ID: {}", id);
            throw new ResourceNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
        logger.info("Product with ID: {} deleted successfully", id);
    }

    @Override
    public List<Product> getProductsByCatId(String catId) {
        logger.info("Fetching products with Category ID: {}", catId);
        List<Product> products = productRepository.findByCatId(catId);
        logger.info("Found {} products with Category ID: {}", products.size(), catId);
        return products;
    }
}
