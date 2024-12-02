package com.smartcontactmanager.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartcontactmanager.entities.MyUser;

public class CustomUserDetails implements UserDetails {

	private MyUser myUser ;
	public CustomUserDetails(MyUser myUser) {
		super();
		this.myUser = myUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		
		return List.of( new SimpleGrantedAuthority(myUser.getRole()));
	}

	@Override
	public String getPassword() {
		
		return myUser.getPassword();
	}

	@Override
	public String getUsername() {
		
		return myUser.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
