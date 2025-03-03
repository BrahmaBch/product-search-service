package com.product.search.model;

import org.springframework.data.mongodb.core.mapping.Field;

public class Category {
	private int id;
	@Field("name")
    private String name;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    

}
