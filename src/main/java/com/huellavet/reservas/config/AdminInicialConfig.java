package com.huellavet.reservas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminInicialConfig {
    @Bean
    CommandLineRunner mostrarHash(
            BCryptPasswordEncoder passwordEncoder
    ){
        return args -> {String hash = passwordEncoder.encode("admin12345");
            System.out.println("Hash de la contraseña 'admin': " + hash);};
    }
}
