package com.Beevago.App.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Beevago.App.repositories.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository ur;

    @Override
    @Async
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ur.findByUserEmail(username);
    }
    
}
