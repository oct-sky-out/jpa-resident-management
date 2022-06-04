package com.nhnacademy.service.security;

import com.nhnacademy.dto.resident.ResidentUserDetails;
import com.nhnacademy.exceptions.UserNotFoundException;
import com.nhnacademy.repository.resident.ResidentRepository;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ResidentRepository residentRepository;
    public CustomUserDetailsService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResidentUserDetails userDetails = residentRepository.findUserDetailsByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("유저 정보가 없습니다."));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_MEMBER");
        return new User(userDetails.getUserId(), userDetails.getPassword(), Collections.singleton(authority));
    }
}
