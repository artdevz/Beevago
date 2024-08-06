package com.Bivago.App.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.Bivago.App.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
  prePostEnabled = true, 
  securedEnabled = true, 
  jsr250Enabled = true)
public class WebSecurityConfiguration {
    
    // private final UserService US;

    @Autowired
    SecurityFilter sf;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {        
        
        http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(request -> {
            request.dispatcherTypeMatchers().permitAll();
            request.requestMatchers(HttpMethod.GET, "/**", "/styles/*", "/login", "/cadastro").permitAll();
            request.requestMatchers(HttpMethod.GET, "/settings/**").permitAll();//.hasRole("USER");
            request.requestMatchers(HttpMethod.POST, "/**", "cadastro/cadastrando", "login/logando").permitAll();
            //request.requestMatchers("/css/**").permitAll();
            //request.authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            request.anyRequest().authenticated();            
        });
        //.httpBasic(Customizer.withDefaults());
        
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //     authProvider.setUserDetailsService(US.userDetailsService());
    //     authProvider.setPasswordEncoder(passwordEncoder);
    //     return authProvider;
    // }

}
