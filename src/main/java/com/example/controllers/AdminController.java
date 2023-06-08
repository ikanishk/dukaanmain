package com.example.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ProductDto;
import com.example.model.Category;
import com.example.model.Product;
import com.example.service.CategoryService;
import com.example.service.ProductService;
//@CrossOrigin("http://localhost:3000/")
@Controller
public class AdminController {

public static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	
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

@PostMapping("/admin/products/add")
public String productaddpost(@ModelAttribute ("productDtO")ProductDto productDto, 
							 @RequestParam("productImage") MultipartFile file,
							 @RequestParam("imgName")String imgName)throws IOException {
	
	Product product=new Product();
	product.setId(productDto.getId());
	product.setName(productDto.getName());
	product.setCategory(categoryService.getcatbyid(productDto.getCategoryId()).get());
	product.setPrice(productDto.getPrice());
	product.setWeight(productDto.getWeight());
	product.setDescription(productDto.getDescription());
	String imageUUID;
	
	if(!file.isEmpty()) {
	imageUUID=file.getOriginalFilename();
	Path FileNameAndPath=Paths.get(uploadDir,imageUUID);
	Files.write(FileNameAndPath, file.getBytes());
	}
	
	else {
	imageUUID=imgName;
	}
	product.setImageName(imageUUID);
	productService.addprod(product);
	return "redirect:/admin/products";

}


@GetMapping("/admin/product/delete/{id}")
public String deleteprod(@PathVariable long id) {
	productService.removeprodbyid(id);
	return "redirect:/admin/products";
	
}


@GetMapping("/admin/product/update/{id}")
public String updateprod(@PathVariable long id,Model model) {
	Product product=productService.getprodbyid(id).get();
	ProductDto productDto = new ProductDto();

	productDto.setId(product.getId());
	productDto.setName(product.getName());
	productDto.setCategoryId(product.getCategory().getId());
	productDto.setPrice(product.getPrice());
	productDto.setWeight(product.getWeight());
	productDto.setDescription(product.getDescription());
	productDto.setImageName(product.getImageName());

	model.addAttribute("categories",categoryService.getallcat());
	model.addAttribute("productDTO",productDto);//why can't i use "prodcutDto instead of "productDTO" to run it?(thymeleaf)
	
	return "productsAdd";
}
}



