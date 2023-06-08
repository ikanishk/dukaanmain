package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dao.RoleRepository;
import com.example.dao.UserRepository;
import com.example.model.Role;

import com.example.model.User;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/register")
	public String registerGet() {
		return "register";
	}
	
	//below route is to register user, and the HttpServletRequest is taken to auto-login the user after he registers.
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user")User user,HttpServletRequest request)throws ServletException {
		System.out.println(user.toString());
		String password=user.getPassword();
	
		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role>roles=new ArrayList<>();
		roles.add(roleRepository.findById(2).get());//gives the user role (id 2) to the new user.
		user.setRoles(roles);
		
		userRepository.save(user);
		
		request.login(user.getEmail(), password);//used to internally login an user after he/she has registered.
		return "redirect:/";
	}

	
}
