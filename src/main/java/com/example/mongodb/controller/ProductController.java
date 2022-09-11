package com.example.mongodb.controller;

import com.example.mongodb.model.Product;
import com.example.mongodb.repository.ProductRepository;
import com.example.mongodb.resource.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts() {

        return ResponseEntity.ok(this.productRepository.findAll());
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());

        return ResponseEntity.status(201).body(this.productRepository.save(product));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity getProductById(@PathVariable String id) {

        Optional<Product> product = this.productRepository.findById(id);

        if(product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.ok("The product with id: " + id + " was not found.");
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteProductById(@PathVariable String id) {

        Optional<Product> product = this.productRepository.findById(id);

        if(product.isPresent()) {
            this.productRepository.deleteById(id);
            return ResponseEntity.ok("Success.");
        } else {
            return ResponseEntity.ok("The product with id: " + id + " was not found.");
        }
    }
}