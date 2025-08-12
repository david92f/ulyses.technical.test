package com.septeo.ulyses.technical.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Configuración de Spring Security.

    // El valor se lee del archivo application.properties
    @Value("${app.user.username}")
    private String userName;

    @Value("${app.user.password}")
    private String password;

    // Permite todas las solicitudes GET y requiere autenticación para POST, PUT y DELETE.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**").permitAll() // Permite el acceso a la consola H2
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/**").permitAll() // Permite GET en /api
                        .anyRequest().authenticated() // Requiere autenticación para todas las demás solicitudes
                )
                .httpBasic(withDefaults()) // Habilita la autenticación HTTP básica
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita CSRF para simplificar las pruebas
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)); // Permite que la consola H2 se muestre en un iframe
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Ahora usamos la variable userPassword en lugar de una cadena fija.
        UserDetails user = User.builder()
                .username(userName)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}