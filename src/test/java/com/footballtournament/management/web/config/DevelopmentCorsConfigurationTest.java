package com.footballtournament.management.web.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DevelopmentCorsConfigurationTest {

    @Test
    void allowsOnlyConfiguredDevelopmentOrigins() throws Exception {
        try (var context = context(true)) {
            var mvc = MockMvcBuilders.webAppContextSetup(context).build();
            mvc.perform(options("/tournaments")
                            .header("Origin", "http://localhost:3000")
                            .header("Access-Control-Request-Method", "POST")
                            .header("Access-Control-Request-Headers", "content-type"))
                    .andExpect(status().isOk())
                    .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"))
                    .andExpect(header().string("Access-Control-Expose-Headers", "X-Response-Message"));
            mvc.perform(options("/tournaments")
                            .header("Origin", "https://unconfigured.example")
                            .header("Access-Control-Request-Method", "POST"))
                    .andExpect(status().isForbidden())
                    .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
        }
    }

    @Test
    void doesNotEnableDevelopmentCorsWithoutDevProfile() throws Exception {
        try (var context = context(false)) {
            assertTrue(context.getBeansOfType(DevelopmentCorsConfiguration.class).isEmpty());
            MockMvcBuilders.webAppContextSetup(context).build()
                    .perform(options("/tournaments")
                            .header("Origin", "http://localhost:3000")
                            .header("Access-Control-Request-Method", "GET"))
                    .andExpect(status().isOk())
                    .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
        }
    }

    private AnnotationConfigWebApplicationContext context(boolean development) {
        var context = new AnnotationConfigWebApplicationContext();
        context.setServletContext(new MockServletContext());
        if (development) {
            context.getEnvironment().setActiveProfiles("dev");
            context.getEnvironment().getPropertySources().addFirst(
                    new org.springframework.core.env.MapPropertySource("test", Map.of(
                            "app.cors.allowed-origins", "http://localhost:3000,http://127.0.0.1:3000")));
        }
        context.register(TestWebConfiguration.class, DevelopmentCorsConfiguration.class);
        context.refresh();
        return context;
    }

    @Configuration
    @EnableWebMvc
    static class TestWebConfiguration {
        @Bean
        TestController testController() {
            return new TestController();
        }
    }

    @RestController
    static class TestController {
        @GetMapping("/tournaments")
        String tournaments() {
            return "[]";
        }
    }
}
