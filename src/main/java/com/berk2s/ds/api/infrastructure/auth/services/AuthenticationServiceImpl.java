package com.berk2s.ds.api.infrastructure.auth.services;

import com.berk2s.ds.api.application.auth.AuthenticationService;
import com.berk2s.ds.api.infrastructure.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticate(String username, String password) {
        SecurityUser user = (SecurityUser) userDetailsService
                .loadUserByUsername(username);

        return passwordEncoder.matches(password, user.getPassword());
    }
}
