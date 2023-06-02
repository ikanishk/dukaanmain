package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dao.UserRepository;
import com.example.model.CustomUserDetail;
import com.example.model.User;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User>user=userRepository.findUserByEmail(email);
		user.orElseThrow(()-> new UsernameNotFoundException("NO SUCH USER FOUND"));
		return user.map(CustomUserDetail::new).get();
	}

}
