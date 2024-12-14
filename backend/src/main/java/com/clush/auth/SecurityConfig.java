package com.clush.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // AuthenticationManager를 설정하기 위한 코드
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .build();
    }

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		 http
		 .csrf(AbstractHttpConfigurer::disable)
         .authorizeHttpRequests(authorize -> authorize
   		 .requestMatchers("/login").permitAll()
   		 .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
        )
        .formLogin(authorize->authorize.disable()
        )
        .httpBasic(authorize->authorize.disable()	
        )
        .sessionManagement((session) -> session
       		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)		//session 사용 안함
        )

		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class
		);
		 return http.build();
	 }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}