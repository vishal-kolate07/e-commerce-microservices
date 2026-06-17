package com.vk.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vk.entity.RegisterEntity;
import com.vk.repository.AuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceApp implements UserDetailsService{
	
	private final AuthRepository repo;
	
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		RegisterEntity existingEntity = repo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Profile Not Found With Email "+email));
		return User.builder()
				   .username(existingEntity.getEmail())
				   .password(existingEntity.getPassword())
				   .authorities(Collections.emptyList())
				   .build();
	}

}
