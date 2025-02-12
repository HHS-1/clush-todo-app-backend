package com.clush.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    //JWT 토큰을 생성 후 return
    public String generateToken(String userId, long id) {
    	Claims claims=Jwts.claims();
    	claims.put("userId", userId);
    	claims.put("id", id);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    

	//refresh토큰 생성
	public String generateRefreshToken(String userId, long id) {
		Claims claims = Jwts.claims();
		claims.put("userId", userId);
		claims.put("id", id);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expirationTime*24))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

    //JWT 토큰에서 Claims 정보를 추출
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    //JWT 토큰에서 사용자 아이디 추출
    public String extractUserId(String token) {
        return extractClaims(token).get("userId").toString();
    }
    public long extractId(String token) {
    	Integer id = (Integer)extractClaims(token).get("id");
    	return id.longValue();
    }
    

    //토큰 만료 여부 체크
    public boolean isTokenExpired(String token) {
    	try {
            // Claims 추출을 시도하되 예외를 처리
            Claims claims = extractClaims(token);
            return claims.getExpiration().before(new Date()); // 만료일자 비교
        } catch (ExpiredJwtException e) {
            // 예외 발생 시 토큰이 만료된 것으로 처리
            return true;
        } catch (Exception e) {
            // 다른 예외 처리 (예: 토큰 형식이 잘못된 경우 등)
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }
    
    // JWT 유효성 검증 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;  // 예외 발생 시 유효하지 않다고 판단
        }
    }
    
    //쿠키에 토큰 저장
    public void saveCookie(HttpServletResponse res, String token) {
    	Cookie accessTokenCookie = new Cookie("accessToken", token);
	    accessTokenCookie.setHttpOnly(true);
	    accessTokenCookie.setPath("/");
	    res.addCookie(accessTokenCookie);
    }
    

}