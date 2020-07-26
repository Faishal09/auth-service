package com.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.repository.UserDetailsRepo;



@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDetailsRepo repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return (UserDetails) repository.getUserDetails(username);
	}
	
	

}
