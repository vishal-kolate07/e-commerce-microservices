package com.vk.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vk.dto.UserResponse;
import com.vk.dto.loginDTO;
import com.vk.dto.registerDTO;
import com.vk.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	
	@PostMapping("/register")
      public ResponseEntity<UserResponse> register(@RequestBody registerDTO info)
      {
		UserResponse response = authService.saveRegister(info);
		return ResponseEntity.status(HttpStatus.CREATED).body(response); 
      }
	
	
	@PostMapping("/login")
	public ResponseEntity<Map<String,Object>> login(@RequestBody loginDTO loginInfo) {
        Map<String,Object> response = authService.saveLogin(loginInfo);
        return ResponseEntity.ok(response);
    }
}
