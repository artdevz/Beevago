package com.Bivago.App.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Bivago.App.repositories.UserRepository;
import com.Bivago.App.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService ts;

    @Autowired
    UserRepository ur;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        var token = this.recoverToken(request);
        var userEmail = this.ts.validationToken(token);

        if (token != null) {
            @SuppressWarnings("unused")
            var subject = ts.validationToken(token);
            UserDetails user = ur.findByUserEmail(userEmail);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);

    }

    private String recoverToken(HttpServletRequest request) {

        var authHeader = request.getHeader("Authorized");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");

    }

}/* */
