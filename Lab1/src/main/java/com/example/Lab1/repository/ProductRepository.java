package com.example.Lab1.repository;

import com.example.Lab1.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}