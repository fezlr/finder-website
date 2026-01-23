package com.bsuir_finder.config;

import com.bsuir_finder.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers("/register/**", "/css/**", "/js/**")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .userDetailsService(userDetailsService);

        return http.build();
    }
}