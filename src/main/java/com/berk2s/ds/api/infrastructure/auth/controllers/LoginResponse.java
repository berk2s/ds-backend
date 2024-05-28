package com.berk2s.ds.api.infrastructure.auth.controllers;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private String accessToken;
}
