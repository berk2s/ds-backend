package com.berk2s.ds.api.application.auth;

import com.nimbusds.jwt.JWTClaimsSet;

public interface TokenService {
    String generateToken(String username);
    JWTClaimsSet parseAndValidate(String token);
    boolean validate(String token);
}
