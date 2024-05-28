package com.berk2s.ds.api.infrastructure.auth.services;

import com.berk2s.ds.api.infrastructure.security.SecurityUser;
import com.berk2s.ds.api.infrastructure.user.jpa.RoleEntity;
import com.berk2s.ds.api.infrastructure.user.jpa.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void shouldAuthenticationSuccessfull() {
        // Given
        String username = "user";
        String password = "password";
        String encodedPassword = "encodedPassword";

        var roleEntity = new RoleEntity();
        roleEntity.setDisplayName("Role");

        var userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(encodedPassword);
        userEntity.setRole(roleEntity);

        SecurityUser user = new SecurityUser(userEntity);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        // When
        boolean isAuthenticated = authenticationService.authenticate(username, password);

        // Then
        assertTrue(isAuthenticated);

        verify(passwordEncoder, times(1)).matches(any(), any());
        verify(userDetailsService, times(1)).loadUserByUsername(any());
    }

    @Test
    public void shouldAuthenticationFails() {
        // Given
        String username = "user";
        String password = "password";
        String encodedPassword = "encodedPassword";

        var roleEntity = new RoleEntity();
        roleEntity.setDisplayName("Role");

        var userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(encodedPassword);
        userEntity.setRole(roleEntity);

        SecurityUser user = new SecurityUser(userEntity);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // When
        boolean isAuthenticated = authenticationService.authenticate(username, password);

        // Then
        assertFalse(isAuthenticated);

        verify(passwordEncoder, times(1)).matches(any(), any());
        verify(userDetailsService, times(1)).loadUserByUsername(any());
    }
}