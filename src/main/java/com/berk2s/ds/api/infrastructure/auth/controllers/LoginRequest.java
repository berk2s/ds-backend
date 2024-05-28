package com.berk2s.ds.api.infrastructure.auth.controllers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LoginRequest {
    @NotNull(message = "username.empty")
    @Size(min = 3, max = 100, message = "username.invalid")
    private String username;

    @NotNull(message = "password.empty")
    @Size(min = 3, max = 100, message = "password.invalid")
    private String password;
}
