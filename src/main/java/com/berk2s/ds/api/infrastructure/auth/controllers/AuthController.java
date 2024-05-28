package com.berk2s.ds.api.infrastructure.auth.controllers;

import com.berk2s.ds.api.application.auth.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(AuthController.ENDPOINT)
@RestController
public class AuthController {
    public static final String ENDPOINT = "/auth";

    private final LoginService loginService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        var accessToken = loginService
                .login(loginRequest.getUsername(), loginRequest.getPassword());

        return new ResponseEntity<>(LoginResponse.builder()
                .accessToken(accessToken)
                .build(), HttpStatus.OK);
    }
}
