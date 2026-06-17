package com.vk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vk.entity.RegisterEntity;

public interface AuthRepository extends JpaRepository<RegisterEntity, Long> {
	
	Optional<RegisterEntity> findByEmail(String email);
	boolean existsByEmail(String email);

}
