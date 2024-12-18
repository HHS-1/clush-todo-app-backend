package com.clush.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 정확한 도메인 명시
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 허용할 메서드 명시
                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With", "Accept") // 허용할 헤더 명시
                .exposedHeaders("Set-Cookie") // 클라이언트에서 접근 가능한 헤더
                .allowCredentials(true) // 인증정보 포함 허용
                .maxAge(3600); // Preflight 요청 캐시 시간
        
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
