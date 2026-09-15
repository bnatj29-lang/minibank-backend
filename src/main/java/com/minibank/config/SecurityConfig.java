package com.minibank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

// Essa classe configura duas coisas essenciais:
//
// 1) O PasswordEncoder: é a "máquina" que transforma a senha digitada
//    (ex: "minhaSenha123") em um hash irreversível (ex: "$2a$10$N9qo8u...").
//    É ISSO que salvamos no banco, nunca a senha em texto puro.
//
// 2) Liberamos por enquanto o endpoint de cadastro para qualquer pessoa acessar
//    sem estar logada (faz sentido: para se cadastrar, a pessoa ainda não tem login).
//    Quando entrarmos na etapa de LOGIN de verdade (com token JWT), vamos restringir
//    as demais rotas.
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/usuarios/cadastro").permitAll()
                .anyRequest().permitAll() // por enquanto liberado; vamos travar quando implementarmos o login/JWT
            );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
