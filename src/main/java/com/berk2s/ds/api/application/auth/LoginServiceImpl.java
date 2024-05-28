package com.berk2s.ds.api.application.auth;

import com.berk2s.ds.api.application.shared.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;

@RequiredArgsConstructor
@Slf4j
@ApplicationService
public class LoginServiceImpl implements LoginService {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @Override
    public String login(String username, String password) {
        boolean isAuthenticated = authenticationService.authenticate(username, password);

        if (!isAuthenticated) {
            log.info("Username or password does not match. [username: {}]", username);
            throw new BadCredentialsException("login.failed");
        }

        return tokenService.generateToken(username);
    }

    @Override
    public void logout() {
        // Out of the task description (nice to have)
        throw new RuntimeException("Method not implemented yet.");
    }
}
