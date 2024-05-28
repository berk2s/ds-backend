package com.berk2s.ds.api.application.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {
    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    public void shouldLoginSucceed() {
        // Given
        String username = "user";
        String password = "pass";
        String expectedToken = "token123";

        when(authenticationService.authenticate(username, password)).thenReturn(true);
        when(tokenService.generateToken(username)).thenReturn(expectedToken);

        // When
        String actualToken = loginService.login(username, password);

        // Then
        assertEquals(expectedToken, actualToken);

        verify(authenticationService, times(1)).authenticate(anyString(), anyString());
        verify(tokenService, times(1)).generateToken(anyString());
    }

    @Test
    public void shouldLoginFails() {
        // Given
        String username = "user";
        String password = "wrongpass";

        when(authenticationService.authenticate(username, password)).thenReturn(false);

        // When
        assertThrows(BadCredentialsException.class, () -> loginService.login(username, password));

        // Then
        verify(authenticationService, times(1)).authenticate(anyString(), anyString());
    }
}