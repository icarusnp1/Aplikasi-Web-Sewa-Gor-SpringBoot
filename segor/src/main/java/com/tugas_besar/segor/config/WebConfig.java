package com.tugas_besar.segor.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/login",
                "/register",
                "/logout",
                "/home",
                "/css/**",
                "/js/**",
                "/images/**",
                "/logreg/home"
            );
    }
}