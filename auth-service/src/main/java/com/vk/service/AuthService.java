package com.vk.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vk.dto.UserResponse;
import com.vk.dto.loginDTO;
import com.vk.dto.registerDTO;
import com.vk.entity.RegisterEntity;
import com.vk.repository.AuthRepository;
import com.vk.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepository repo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	
	
	
	
	
	public UserResponse saveRegister(registerDTO dto)
	{
		if(repo.existsByEmail(dto.getEmail())){
			
			throw new IllegalArgumentException("Email already registered: " + dto.getEmail());
		}
		
		RegisterEntity toEntity = RegisterEntity.builder()
				                .email(dto.getEmail())
				                .userName(dto.getUserName())
				                .password(passwordEncoder.encode(dto.getPassword()))
				                .build();
		
		RegisterEntity save = repo.save(toEntity);
		
		return toResponse(save);
	}
	
	
	private UserResponse toResponse(RegisterEntity entity)
	{
		return new UserResponse(
				entity.getId(),
				entity.getUserName(),
				entity.getEmail(),
				entity.getCreatedAt()
				);
	}
//===========================================================================================
	
	
	public Map<String,Object> saveLogin(loginDTO loginInfo)
	{
		try {
			 
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInfo.getEmail(), loginInfo.getPassword()));
			String token = jwtUtil.generateToken(loginInfo.getEmail());
			RegisterEntity currentProfile = repo.findByEmail(loginInfo.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
			
			return Map.of(
					 "user",toResponse(currentProfile),
			         "token",token
			         );
		}
        catch (Exception e) {
			
			throw new RuntimeException("Invalid Email or Password.");
		}				
	}
}
