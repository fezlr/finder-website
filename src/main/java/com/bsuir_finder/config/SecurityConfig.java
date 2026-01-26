package com.bsuir_finder.config;

import com.bsuir_finder.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                )
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers("/register/**", "/css/**", "/js/**", "/login/**", "/api/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(req -> req
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )

                .logout(
                        Customizer.withDefaults()
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }
}