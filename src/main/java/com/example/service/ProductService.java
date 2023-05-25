package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.CategoryDao;
import com.example.dao.ProductDao;
import com.example.model.Category;
import com.example.model.Product;

@Service
public class ProductService {
	@Autowired
	ProductDao productDao;

	public void addprod(Product product) {productDao.save(product);}

	public List<Product> getallprod(){return productDao.findAll();}

	public void removeprodbyid(long id) {productDao.deleteById(id);}
	
	public Optional<Product> getprodbyid(long id){return productDao.findById(id);}
}
