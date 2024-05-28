package com.berk2s.ds.api.infrastructure.security;

import com.berk2s.ds.api.infrastructure.shared.ResourceNotFoundException;
import com.berk2s.ds.api.infrastructure.user.jpa.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserJPARepository userJPARepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userJPARepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User with given username doesn't exists. [username: {}]", username);
                    return new ResourceNotFoundException("user.notFound");
                });

        return new SecurityUser(user);
    }
}
