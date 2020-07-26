package com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.JwtRequest;
import com.auth.dto.JwtResponse;
import com.auth.repository.UserDetailsRepo;
import com.auth.service.JwtUserDetailsService;
import com.auth.utils.JwtTokenUtil;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private UserDetailsRepo repository;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		//repository.addUserDetails(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		jwtTokenUtil.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return new ResponseEntity(new JwtResponse(token),HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public ResponseEntity<UserDetails> findUserByUserName(@PathVariable("username") String name,
			@RequestHeader("authorization") String token
			) throws Exception {
	     final UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(name);
	     if(!jwtTokenUtil.validateToken(token, userDetails)) {
	    	 return new ResponseEntity("Not valid token",HttpStatus.BAD_GATEWAY);
	     }
		return new ResponseEntity(userDetails,HttpStatus.OK);
	}

	
	
}
