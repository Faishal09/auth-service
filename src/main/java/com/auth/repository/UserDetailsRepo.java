package com.auth.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.auth.dto.JwtUser;


@Repository
public class UserDetailsRepo {
	
	public static List<JwtUser> details= new ArrayList<>();
	
	public  static List<JwtUser> initializeData() {
		
		return details;
	}
	
	public JwtUser getUserDetails(String name) {
		List<JwtUser> details=initializeData();
		JwtUser userObj=null;
		for(JwtUser user:details) {
			
			if(user.getUsername()==name) {
				userObj=user;
				return userObj;
			}
		}
		if (userObj == null) {
			throw new UsernameNotFoundException("Bad credentials");
		}
		       

	    JwtUser user= new JwtUser(userObj.getUsername(), userObj.getPassword(), userObj, true);
	    
	    details.add(user);
	    return user;
	
	}
	
	
	

}
