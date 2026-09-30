package com.minibank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Stream;

@Configuration
public class ConfiguracaoCorsMinibank {

    @Value("${minibank.frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Bean
    public CorsConfigurationSource configuracaoCors() {
        CorsConfiguration configuracao = new CorsConfiguration();
        configuracao.setAllowedOrigins(Stream.of("http://localhost:5173", normalizarOrigem(frontendUrl))
                .distinct()
                .toList());
        configuracao.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuracao.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        configuracao.setAllowCredentials(false);

        // Aplica a mesma permissão a todos os endpoints, inclusive os novos.
        UrlBasedCorsConfigurationSource origem = new UrlBasedCorsConfigurationSource();
        origem.registerCorsConfiguration("/**", configuracao);
        return origem;
    }

    private String normalizarOrigem(String origem) {
        if (origem == null || origem.isBlank()) {
            return "http://localhost:5173";
        }

        String normalizada = origem.trim();
        if (normalizada.length() >= 2 && normalizada.startsWith("\"") && normalizada.endsWith("\"")) {
            normalizada = normalizada.substring(1, normalizada.length() - 1).trim();
        }
        while (normalizada.endsWith("/")) {
            normalizada = normalizada.substring(0, normalizada.length() - 1);
        }
        return normalizada;
    }
}
