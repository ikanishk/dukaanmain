package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import com.example.dao.CategoryDao;
import com.example.model.Category;

@Service
public class CategoryService {
@Autowired
CategoryDao categoryDao;

public void addcat(Category category) {categoryDao.save(category);}

public List<Category> getallcat(){return categoryDao.findAll();}

public void removecatbyid(int id) {categoryDao.deleteById(id);}

public Optional<Category> getcatbyid(int id){return categoryDao.findById(id);}
}
