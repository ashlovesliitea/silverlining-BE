package com.example.capstone;


import com.example.capstone.config.interceptor.AuthenticationInterceptor;
import com.example.capstone.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry reg){
        reg.addInterceptor(new AuthenticationInterceptor(jwtService,objectMapper))
                .order(1)
                .addPathPatterns("/app/**");
        //.excludePathPatterns
    }
}
