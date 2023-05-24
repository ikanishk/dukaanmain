package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Product;

public interface ProductDao extends JpaRepository<Product, Long> {

}
