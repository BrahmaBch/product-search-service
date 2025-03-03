package com.product.search.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.search.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	@Query("{ 'categories.name' : ?0 }")
	List<Product> findByCategoriesName(String categoryName);
    List<Product> findByTagsContaining(String tagName);
}
