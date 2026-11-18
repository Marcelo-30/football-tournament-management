package com.footballtournament.management.web.config;

import com.footballtournament.management.web.response.ResponseHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("dev")
public class DevelopmentCorsConfiguration implements WebMvcConfigurer {

    private final String[] allowedOrigins;

    public DevelopmentCorsConfiguration(
            @Value("${app.cors.allowed-origins}") String[] allowedOrigins
    ) {
        this.allowedOrigins = allowedOrigins;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "DELETE", "OPTIONS")
                .allowedHeaders("Accept", "Content-Type")
                .exposedHeaders(ResponseHeader.MESSAGE)
                .maxAge(3600);
    }
}
