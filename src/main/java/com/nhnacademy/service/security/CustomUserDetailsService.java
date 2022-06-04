package com.nhnacademy.service.security;

import com.nhnacademy.service.ResidentService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ResidentService residentService;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(ResidentService residentService, PasswordEncoder passwordEncoder) {
        this.residentService = residentService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
