package com.huellavet.reservas.config;

import com.huellavet.reservas.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/registro").permitAll()
                        .requestMatchers("/api/veterinario/login").permitAll()
                        .requestMatchers("/api/admin/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/servicios/**", "/api/tipos-servicio/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/mascotas/**").hasRole("USUARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/mascotas/**").hasRole("USUARIO")
                        .requestMatchers(HttpMethod.DELETE, "/api/mascotas/**").hasRole("USUARIO")
                        .requestMatchers(HttpMethod.GET, "/api/mascotas/**").hasAnyRole("USUARIO", "VETERINARIO", "ADMINISTRADOR")

                        .requestMatchers(HttpMethod.POST, "/api/citas/**").hasRole("USUARIO")
                        .requestMatchers("/api/citas/*/aceptar", "/api/citas/*/rechazar", "/api/citas/*/completar")
                        .hasAnyRole("VETERINARIO", "ADMINISTRADOR")
                        .requestMatchers("/api/citas/*/cancelar", "/api/citas/*/reprogramar")
                        .hasAnyRole("USUARIO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/citas/**").hasAnyRole("USUARIO", "VETERINARIO", "ADMINISTRADOR")

                        .requestMatchers(HttpMethod.POST, "/api/servicios/**").hasAnyRole("VETERINARIO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/servicios/**").hasAnyRole("VETERINARIO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/servicios/**").hasAnyRole("VETERINARIO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/api/tipos-servicio/**").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/tipos-servicio/**").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/tipos-servicio/**").hasRole("ADMINISTRADOR")

                        .requestMatchers(HttpMethod.POST, "/api/veterinario/**").hasRole("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/veterinario/**").hasRole("ADMINISTRADOR")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/servicios/**", "/api/tipos-servicio/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}