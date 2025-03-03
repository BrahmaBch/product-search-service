# product-search-service

This is a Spring Boot RESTful API to manage products, with the ability to filter by categories and tags.

## Features

1. **Show all products**: GET `http://localhost:8081/api/products/show_all`
2. **Search by Category**: GET `http://localhost:8081/api/products/categories/Coverings`
3. **Search by Tag**: GET `http://localhost:8081/api/products/tags/new-product`
4. **Initialize Data**: POST `http://localhost:8081/api/products/init` (Inserts sample product data)

## Requirements

- Java 17
- Maven
- Spring boot 3.4.3
- MongoDB

## Setup

1. Clone this repository.
2. Run `mvn spring-boot:run` to start the application.

## Test

To test the API, use tools like Postman

