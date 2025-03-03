package com.product.search.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.search.model.Product;
import com.product.search.repository.ProductRepository;

@Service
public class ProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
    private ProductRepository productRepository;

	public List<Product> getAllProducts() {
        logger.info("Fetching all products.");
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                logger.warn("No products found.");
            }
            return products;

        } catch (Exception e) {
            logger.error("Error fetching all products.", e);
            throw new RuntimeException("Failed to fetch all products");
        }
    }

    public List<Product> getProductsByTag(String tagName) {
        logger.info("Fetching products for tag: {}", tagName);

        try {
            List<Product> products = productRepository.findByTagsContaining(tagName);

            if (products.isEmpty()) {
                logger.warn("No products found for tag: {}", tagName);
            }

            return products;

        } catch (Exception e) {
            logger.error("Error fetching products for tag: {}", tagName, e);
            throw new RuntimeException("Failed to fetch products by tag");
        }
    }

    public List<Product> getProductsByCategory(String categoryName) {
        logger.info("Fetching products for category: {}", categoryName);

        try {
            List<Product> products = productRepository.findByCategoriesName(categoryName);
            if (products.isEmpty()) {
                logger.warn("No products found for category: {}", categoryName);
            }
            return products;

        } catch (Exception e) {
            logger.error("Error fetching products for category: {}", categoryName, e);
            throw new RuntimeException("Failed to fetch products by category"); 
        }
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
