package com.bsuir_finder.security.config;

import com.bsuir_finder.service.CustomUserDetailsService;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final AdminServerProperties adminServer;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          AdminServerProperties adminServer) {
        this.userDetailsService = userDetailsService;
        this.adminServer = adminServer;
    }

    /** ADMIN UI security */
    @Bean
    @Order(1) // самый высокий приоритет
    public SecurityFilterChain securityAdminFilterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminServer.path("/"));

        http
                // применять только к /admin/**
                .securityMatcher(adminServer.path("/**"))
                .authorizeHttpRequests(auth -> auth
                        // разрешаем статику и страницу логина Admin UI
                        .requestMatchers(
                                adminServer.path("/assets/**"),
                                adminServer.path("/favicon.ico"),
                                adminServer.path("/login"),
                                adminServer.path("/actuator/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage(adminServer.path("/login"))
                        .successHandler(successHandler)
                )
                .logout(logout -> logout
                        .logoutUrl(adminServer.path("/logout"))
                )
                // Basic нужен для регистрации клиентов SBA
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                adminServer.path("/instances"),
                                adminServer.path("/actuator/**")
                        )
                );

        return http.build();
    }

    /** USER UI security */
    @Bean
    @Order(2) // вторым
    public SecurityFilterChain securityUserFilterChain(HttpSecurity http) throws Exception {
        http
                // применять только к /user/**
                .securityMatcher("/user/**")
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico"
                        ).permitAll()
                        .requestMatchers(
                                "/user/login",
                                "/user/register",
                                "/api/auth/register"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .failureUrl("/user/login?error")
                        .defaultSuccessUrl("/user/home", true)
                        .permitAll()
                )
                .logout(Customizer.withDefaults())
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /** CATCH‑ALL (если есть другие пути) — можно оставить неавторизованным */
    @Bean
    @Order(3)
    public SecurityFilterChain fallbackFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}