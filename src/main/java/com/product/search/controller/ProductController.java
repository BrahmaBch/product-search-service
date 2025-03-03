package com.product.search.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.search.model.Category;
import com.product.search.model.Product;
import com.product.search.service.ProductService;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
    private ProductService productService;

    @GetMapping("/show_all")
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("Received request to get all products.");
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                logger.warn("No products found.");
                return ResponseEntity.notFound().build();
            }
            logger.info("Found {} products.", products.size());
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error while fetching all products.", e);
            return ResponseEntity.status(500).build(); 
        }
    }

    @GetMapping("/categories/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("categoryName") String categoryName) {
        logger.info("Received request to get products by category: {}", categoryName);
        if (categoryName == null || categoryName.trim().isEmpty()) {
            logger.error("Invalid category name: {}", categoryName);
            return ResponseEntity.badRequest().build();
        }
        try {
            List<Product> products = productService.getProductsByCategory(categoryName);
            if (products.isEmpty()) {
                logger.warn("No products found for category: {}", categoryName);
                return ResponseEntity.notFound().build(); 
            }

            logger.info("Found {} products for category: {}", products.size(), categoryName);
            return ResponseEntity.ok(products);

        } catch (Exception e) {
            logger.error("Error while fetching products by category: {}", categoryName, e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/tags/{tagName}")
    public ResponseEntity<List<Product>> getProductsByTag(@PathVariable("tagName") String tagName) {
        logger.info("Received request to get products by tag: {}", tagName);
        if (tagName == null || tagName.trim().isEmpty()) {
            logger.error("Invalid tag name: {}", tagName);
            return ResponseEntity.badRequest().build();
        }
        try {
            List<Product> products = productService.getProductsByTag(tagName);
            if (products.isEmpty()) {
                logger.warn("No products found for tag: {}", tagName);
                return ResponseEntity.notFound().build();
            }
            logger.info("Found {} products for tag: {}", products.size(), tagName);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error while fetching products by tag: {}", tagName, e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/init")
    public String initData() {
        Category category1 = new Category();
        category1.setId(1);
        category1.setName("Coverings");

        Category category2 = new Category();
        category2.setId(2);
        category2.setName("Building products");
        
        Category category3 = new Category();
        category2.setId(3);
        category2.setName("End product");
        
        Category category4 = new Category();
        category2.setId(4);
        category2.setName("Insulation");

        Product product1 = new Product();
        product1.setUuid("0d835b0d-4d4e-46cc-88e1-5169f638f6dc");
        product1.setName("Paint");
        product1.setCategories(List.of(category1, category3));
        product1.setTags(List.of("new-product"));

        Product product2 = new Product();
        product2.setUuid("57f318aa-75ee-4b4b-a7c0-e17400b91897");
        product2.setName("Mortar");
        product2.setCategories(List.of(category2, category3));
        product2.setTags(List.of(""));
        
        Product product3 = new Product();
        product3.setUuid("75ee-4baa-0d835bb-a7c0-e174005169f7");
        product3.setName("Expanded cork");
        product3.setCategories(List.of(category4, category2));
        product3.setTags(List.of("bio-based"));

        productService.saveProduct(product1);
        productService.saveProduct(product2);
        productService.saveProduct(product3);

        return "Data Initialized!";
    }


}
