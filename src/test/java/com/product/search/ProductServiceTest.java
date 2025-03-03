package com.product.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.product.search.model.Category;
import com.product.search.model.Product;
import com.product.search.repository.ProductRepository;
import com.product.search.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;
    private Category electronicsCategory;
    private Category furnitureCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        electronicsCategory = new Category(1, "Electronics");
        furnitureCategory = new Category(2, "Furniture");

        product1 = new Product();
        product1.setUuid("uuid-001");
        product1.setName("Laptop");
        product1.setCategories(Arrays.asList(electronicsCategory));
        product1.setTags(Arrays.asList("Technology", "Portable"));

        product2 = new Product();
        product2.setUuid("uuid-002");
        product2.setName("Sofa");
        product2.setCategories(Arrays.asList(furnitureCategory));
        product2.setTags(Arrays.asList("Comfort", "Furniture"));
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertEquals("Laptop", products.get(0).getName());
        assertEquals("Sofa", products.get(1).getName());
    }

    @Test
    void testGetProductsByCategory() {
        when(productRepository.findByCategoriesName("Electronics")).thenReturn(Arrays.asList(product1));
        List<Product> products = productService.getProductsByCategory("Electronics");
        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).getName());
    }

    @Test
    void testGetProductsByTag() {
        when(productRepository.findByTagsContaining("Technology")).thenReturn(Arrays.asList(product1));
        List<Product> products = productService.getProductsByTag("Technology");
        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).getName());
    }
    
    @Test
    void testGetProductsByCategoryWhenNoProductsFound() {
        when(productRepository.findByCategoriesName("NonExistentCategory")).thenReturn(Collections.emptyList());
        List<Product> products = productService.getProductsByCategory("NonExistentCategory");
        assertTrue(products.isEmpty());
    }
    
    @Test
    void testGetProductsByTagWhenNoProductsFound() {
        when(productRepository.findByTagsContaining("NonExistentTag")).thenReturn(Collections.emptyList());
        List<Product> products = productService.getProductsByTag("NonExistentTag");
        assertTrue(products.isEmpty());
    }
    
    @Test
    void testGetProductsByCategoryWithMultipleProducts() {
        when(productRepository.findByCategoriesName("Electronics")).thenReturn(Arrays.asList(product1, product2));
        List<Product> products = productService.getProductsByCategory("Electronics");
        assertEquals(2, products.size());
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Laptop")));
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Sofa")));
    }
    
    @Test
    void testGetAllProductsWhenNoProductsFound() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> products = productService.getAllProducts();
        assertTrue(products.isEmpty());
    }


}

