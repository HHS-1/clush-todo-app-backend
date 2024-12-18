package com.clush.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clush.dto.UserDto;
import com.clush.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody UserDto userDto, HttpServletResponse res, HttpServletRequest req){
		return userService.loginService(userDto, res, req);
	}
	
	@PostMapping("sign-up")
	public ResponseEntity<Void> signUp(@RequestBody UserDto userDto){
		return userService.signUpService(userDto);
	}
	
	@GetMapping("check")
	public ResponseEntity<Void> checkLogin(HttpServletRequest req){
		return userService.checkLoginService(req);
	}
	
	@GetMapping("logout")
	public void logout(HttpServletResponse res) {
		userService.logoutService(res);
	}
}
