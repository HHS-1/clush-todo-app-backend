package com.clush.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clush.dto.UserDto;
import com.clush.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody UserDto userDto, HttpServletResponse res){
		return userService.loginService(userDto, res);
	}
	
	@PostMapping
	public ResponseEntity<Void> signUp(@RequestBody UserDto userDto){
		return userService.signUpService(userDto);
	}
}
