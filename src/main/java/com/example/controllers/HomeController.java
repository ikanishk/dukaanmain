package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.service.CategoryService;
import com.example.service.ProductService;

@Controller
public class HomeController {
@Autowired
CategoryService categoryService;

@Autowired
ProductService productService;

@GetMapping({"/","/home"}) //if we want the route to work for multiple URLs, then all of them should be passed as an array.
public String home(Model model) {
	return "index";
}

@GetMapping("/shop")
public String shop(Model model) {
	
	model.addAttribute("categories",categoryService.getallcat());
	model.addAttribute("products",productService.getallprod());
	return "shop";
}

@GetMapping("/shop/category/{id}")
public String shopbycat(Model model, @PathVariable int id) {
	
	model.addAttribute("categories",categoryService.getallcat());
	model.addAttribute("products",productService.getprodbycatid(id));
	return "shop";
}

@GetMapping("/shop/viewproduct/{id}")
public String viewProduct(Model model, @PathVariable int id) {
	
	model.addAttribute("product",productService.getprodbyid(id).get());

	return "viewProduct";
}

}
