package com.Beevago.App.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    
    // private final UserService US;

    @Autowired
    SecurityFilter sf;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {        
        
        return http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(request -> request
            .requestMatchers(HttpMethod.GET, "/", "/auth/login", "/auth/register", "/css/**", "/image/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/register/**").permitAll()
            .anyRequest().authenticated()
            )
            .addFilterBefore(sf, UsernamePasswordAuthenticationFilter.class)
            .build();
        
        
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
