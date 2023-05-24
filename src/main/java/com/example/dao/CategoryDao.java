package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Category;

public interface CategoryDao extends JpaRepository<Category, Integer> {

}
