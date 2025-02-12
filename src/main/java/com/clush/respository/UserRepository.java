package com.clush.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clush.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long>{
	UserEntity findByUserId(String userId);
	Boolean existsByUserId(String userId);
} 
