package com.example.Lab1.controllers;

import com.example.Lab1.models.Product;
import com.example.Lab1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public Iterable<Product> retrieveAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product retrieveProduct(@PathVariable Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @PostMapping
    public @ResponseBody String createProduct(@RequestParam String name, @RequestParam Double price) {
        Product p = new Product(name, price);
        productRepository.save(p);
        return "Product with id: " + p.getId() + " added successfully";
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Integer id, @RequestParam String name, @RequestParam Double price) {
        Product p = new Product(id, name, price);
        productRepository.save(p);
        return "Product with id: " + id + " updated successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return "Product with id: " + id + " deleted successfully";
    }
}