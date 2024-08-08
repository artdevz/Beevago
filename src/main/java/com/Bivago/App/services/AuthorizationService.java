package com.Bivago.App.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Bivago.App.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository ur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ur.findByUserEmail(username);
    }
    
}

/*package com.Bivago.App.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.Bivago.App.dto.AcessDTO;
import com.Bivago.App.dto.AuthenticationDTO;
import com.Bivago.App.security.jwt.JwtUtils;

@Service
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public AcessDTO login(AuthenticationDTO authDto) {

        try {

        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(authDto.getName(), authDto.getPassword());
        
        Authentication authentication = authenticationManager.authenticate(userAuth);

        UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();
        
        String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
    
        AcessDTO accessDto = new AcessDTO(token);

        return accessDto;

        } catch (BadCredentialsException e) {

        }

        return new AcessDTO("Acesso Negado");

    }

}
*/