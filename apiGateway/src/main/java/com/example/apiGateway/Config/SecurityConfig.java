package com.example.apiGateway.Config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/room-service/**").permitAll()
                        .pathMatchers("/user-service/register", "/user-service/login").permitAll()
                        .pathMatchers("/video-service/**").permitAll()
                        .pathMatchers("/*/swagger-ui/**", "/*/api-docs/**").permitAll()
                        .pathMatchers("/swagger-ui/**", "/webjars/**", "/v3/api-docs/**").permitAll() // Permit Swagger and WebJars paths
                        .anyExchange().permitAll()
                )
                .build();
    }

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("https://15f8-2409-40c2-2048-64bd-4426-1313-6425-13e.ngrok-free.app");
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://157.33.203.18:5173");
        config.addAllowedOrigin(System.getenv("ALLOWED_ORIGIN_1"));
        config.addAllowedOrigin(System.getenv("ALLOWED_ORIGIN_2"));
       // config.addAllowedOrigin(("https://15f8-2409-40c2-2048-64bd-4426-1313-6425-13e.ngrok-free.app","http://localhost:5173"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
