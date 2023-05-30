package com.example.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail extends  User implements UserDetails {

	
	//Why do we need a constructor here?
	public CustomUserDetail(User user) {
		super(user);
	}

	//Understand below function
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority>authoritylist=new ArrayList<>();
		super.getRoles().forEach(role ->{
			authoritylist.add(new SimpleGrantedAuthority(role.getName()));
		});
		return authoritylist;
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}
	
	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
