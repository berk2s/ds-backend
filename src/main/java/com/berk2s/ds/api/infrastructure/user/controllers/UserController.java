package com.berk2s.ds.api.infrastructure.user.controllers;

import com.berk2s.ds.api.infrastructure.security.SecurityUser;
import com.berk2s.ds.api.infrastructure.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(UserController.ENDPOINT)
@RestController
public class UserController {
    public static final String ENDPOINT = "/users";

    @GetMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(Authentication authentication) {
        return new ResponseEntity<>(
                UserInfoResponse.fromEntity(((SecurityUser) authentication.getPrincipal()).getUser()),
                HttpStatus.OK);
    }
}
