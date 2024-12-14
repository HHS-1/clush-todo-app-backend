package com.clush.auth;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.clush.util.CookieUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 쿠키에서 JWT 액세스 토큰 추출
        String accessToken= CookieUtil.getCookie(request, "accessToken");

        // 토큰이 존재하고 유효하면 인증 처리
        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            String userName = jwtUtil.extractUserId(accessToken);

            // 인증 정보 생성
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(userName, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // SecurityContext에 인증 정보 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);  // 필터 체인에 요청 전달
    }
}
