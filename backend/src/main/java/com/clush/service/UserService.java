package com.clush.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clush.auth.JwtUtil;
import com.clush.dto.UserDto;
import com.clush.entity.UserEntity;
import com.clush.respository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public ResponseEntity<Void> loginService(UserDto userDto, HttpServletResponse res){
		String userId = userDto.getUserId();
		
		UserEntity user = userRepository.findByUserId(userId);
		if(user == null) {
			return ResponseEntity.status(404).build();
		}
		
		boolean isPassWordVaild = passwordEncoder.matches(userDto.getUserPassWord(), user.getUserPassWord());
		
		if(isPassWordVaild) {
			String accessToken = jwtUtil.generateToken(userId);
			String refreshToken = jwtUtil.generateRefreshToken(userId);
			
			jwtUtil.saveCookie(res, accessToken); //HttpOnly 쿠키에 엑세스토큰 저장
			redisTokenService.saveRefreshToken(userId, refreshToken, 3600*24*10); //redis에 리프레시토큰 저장
			
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.status(404).build();
		}
		
		
	}
	
	public ResponseEntity<Void> signUpService(UserDto userDto){

		if(userRepository.existsByUserId(userDto.getUserId())){
			return ResponseEntity.status(400).build();
		}
		
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(userDto.getUserId());
		userEntity.setUserPassWord(passwordEncoder.encode(userDto.getUserPassWord()));
		
		userRepository.save(userEntity);
		
		return ResponseEntity.ok().build();
	}
	
}
