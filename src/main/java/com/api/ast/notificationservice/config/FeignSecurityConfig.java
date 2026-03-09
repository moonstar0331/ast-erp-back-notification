package com.api.ast.notificationservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignSecurityConfig {

    @Bean
    public RequestInterceptor jwtFeignRequestInterceptor() {
        return requestTemplate -> {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                return;
            }

            Object credentials = authentication.getCredentials();

            if (credentials instanceof String) {
                String token = (String) credentials;
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
