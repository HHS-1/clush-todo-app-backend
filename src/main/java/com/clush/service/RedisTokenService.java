package com.clush.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class RedisTokenService {
	
	private final RedisTemplate<String, Object> redisTemplate;

    public RedisTokenService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 리프레시 토큰 저장
    public void saveRefreshToken(String accessToken, String refreshToken, long duration) {
        redisTemplate.opsForValue().set(
        	accessToken, 
            refreshToken, 
            Duration.ofMillis(duration) // 토큰 만료 시간 설정
        );
    }

    // 리프레시 토큰 조회
    public String getRefreshToken(String accessToken) {
        return (String) redisTemplate.opsForValue().get(accessToken);
    }

    // 리프레시 토큰 삭제
    public void deleteRefreshToken(String userId) {
        redisTemplate.delete(userId);
    }
}
