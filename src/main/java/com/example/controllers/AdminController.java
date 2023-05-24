package com.example.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dto.ProductDto;
import com.example.model.Category;
import com.example.service.CategoryService;
import com.example.service.ProductService;

@Controller
public class AdminController {

@Autowired
CategoryService categoryService;

@Autowired
ProductService productService;

@GetMapping("/admin")
public String adminHome() {
	return "adminHome";
}

@GetMapping("/admin/categories")
public String getcat(Model model) {
	model.addAttribute("categories", categoryService.getallcat());
	return "categories";
}

@GetMapping("/admin/categories/add")
public String getcatadd(Model model) {
	model.addAttribute("category",new Category());
	return "categoriesAdd";
}

@PostMapping("/admin/categories/add")
public String postcatadd(@ModelAttribute("category")Category category) {
	categoryService.addcat(category);
	
	return "redirect:/admin/categories";
}

@GetMapping("/admin/categories/delete/{id}")
public String deletecat(@PathVariable int id) {
	categoryService.removecatbyid(id);
	return "redirect:/admin/categories";
	
}

@GetMapping("/admin/categories/update/{id}")
public String updatecat(@PathVariable int id,Model model) {
	Optional<Category>category=categoryService.getcatbyid(id);
	if(category.isPresent()) {
		model.addAttribute("category",category.get());
		return "categoriesAdd";
	}
	else {
		return "404";
	}

	
}

//Product Section
@GetMapping("/admin/products")
public String getprod(Model model) {
	model.addAttribute("products", productService.getallprod());
	return "products";
	
}

@GetMapping("/admin/products/add")
public String prodadd(Model model) {
	model.addAttribute("productDTO", new ProductDto());
	model.addAttribute("categories", categoryService.getallcat());

	return "productsAdd";
	
}

}

