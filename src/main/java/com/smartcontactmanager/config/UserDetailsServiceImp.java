package com.smartcontactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smartcontactmanager.entities.MyUser;
import com.smartcontactmanager.userRepo.UserDao;

public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired UserDao userDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser myUser = userDao.getUserByUserName(username);
		if(myUser == null) {
			throw new UsernameNotFoundException("Invalid User");
		}
		
		CustomUserDetails  customUserDetails= new CustomUserDetails(myUser);
		return customUserDetails;
	}

}
