package com.vk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.vk.service.UserDetailsServiceApp;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class securityConfig {


	private final UserDetailsServiceApp userDetailsServiceApp;
	
	
	
	 @Bean
	   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		 System.out.println("SecurityConfig Loaded...");
		 
	        return http
	                .csrf(AbstractHttpConfigurer::disable)
	                .authorizeHttpRequests(auth -> auth
	                        .anyRequest().permitAll()
	                )
	                .httpBasic(Customizer.withDefaults())
	                .build();
	    }
	
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	    	return new BCryptPasswordEncoder();
	    }
	 
	 
	 
	 @Bean
	    public AuthenticationManager authenticationManager() {
	    	
	    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	    	authenticationProvider.setUserDetailsService(userDetailsServiceApp);
	    	authenticationProvider.setPasswordEncoder(passwordEncoder());
	    	
	    	return new ProviderManager(authenticationProvider);
	    	
	    }
	    
	
}
