package com.clush.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.List;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private final CustomUserDetails principal;  // CustomUserDetails로 principal 변경
    private String credentials;

    // 인증되지 않은 사용자용 생성자
    public JwtAuthenticationToken(CustomUserDetails principal, String credentials) {
        super(null);  
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    // 인증된 사용자용 생성자 (권한을 전달)
    public JwtAuthenticationToken(CustomUserDetails principal, String credentials, List<GrantedAuthority> authorities) {
        super(authorities);  // 권한을 부모 클래스에 전달
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);  // 인증된 상태로 설정
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;  
    }
}
