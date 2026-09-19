package com.minibank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class ConfiguracaoCorsMinibank {

    @Bean
    public CorsConfigurationSource configuracaoCors() {
        CorsConfiguration configuracao = new CorsConfiguration();
        String frontendUrl = System.getenv().getOrDefault("FRONTEND_URL", "http://localhost:5173");
        configuracao.setAllowedOrigins(List.of("http://localhost:5173", frontendUrl));
        configuracao.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuracao.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        configuracao.setAllowCredentials(false);

        // Aplica a mesma permissão a todos os endpoints, inclusive os novos.
        UrlBasedCorsConfigurationSource origem = new UrlBasedCorsConfigurationSource();
        origem.registerCorsConfiguration("/**", configuracao);
        return origem;
    }
}
