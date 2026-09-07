 package com.government.subsidy.config;

import com.government.subsidy.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(
            CustomUserDetailsService customUserDetailsService) {

        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        customUserDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                // Enable CORS for React frontend
                .cors(cors -> {
                })

                .authenticationProvider(authenticationProvider())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/users")
                        .permitAll()

                        .requestMatchers("/api/auth/me")
                        .authenticated()

                        // Temporary: Allow new user registration
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/users")
                        .permitAll()

                        // ADMIN → User management
                        .requestMatchers("/api/users/**")
                        .hasRole("ADMIN")

                        // ADMIN → Audit Logs
                        .requestMatchers("/api/audit-logs/**")
                        .hasRole("ADMIN")

                        // ADMIN → Scheme management
                        .requestMatchers("/api/schemes/**")
                        .hasRole("ADMIN")

                        // ADMIN → Dashboard
                        .requestMatchers("/api/dashboard/**")
                        .hasRole("ADMIN")

                        // ADMIN → Analytics
                        .requestMatchers("/api/analytics/**")
                        .hasRole("ADMIN")

                        // ADMIN → Fund Utilization Analytics
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/fund-utilizations/summary")
                        .hasRole("ADMIN")

                        // FIELD OFFICER
                        .requestMatchers("/api/beneficiaries/**")
                        .hasAnyRole("FIELD_OFFICER", "ADMIN")

                        .requestMatchers("/api/applications/**")
                        .hasAnyRole("FIELD_OFFICER", "ADMIN")

                        // DISTRICT OFFICER
                        .requestMatchers("/api/verifications/**")
                        .hasAnyRole(
                                "DISTRICT_OFFICER",
                                "ADMIN")

                        // DISTRICT OFFICER → Milestone management
                        .requestMatchers("/api/milestones/**")
                        .hasAnyRole(
                                "DISTRICT_OFFICER",
                                "ADMIN")

                        // FINANCE APPROVER
                        .requestMatchers("/api/disbursements/**")
                        .hasAnyRole(
                                "FINANCE_APPROVER",
                                "ADMIN")

                        // FIELD OFFICER → Document management
                        .requestMatchers("/api/documents/**")
                        .hasAnyRole(
                                "FIELD_OFFICER",
                                "ADMIN")

                        // Other APIs require login
                        .anyRequest()
                        .authenticated()
                )

                .httpBasic(httpBasic -> {
                })

                .formLogin(
                        AbstractHttpConfigurer::disable
                );

        return http.build();
    }

    // React Frontend CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:5173",
                        "http://localhost:5174"
                ));

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                ));

        configuration.setAllowedHeaders(
                List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration);

        return source;
    }
}
