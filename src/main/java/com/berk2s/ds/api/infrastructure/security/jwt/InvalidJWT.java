package com.berk2s.ds.api.infrastructure.security.jwt;

public class InvalidJWT extends RuntimeException {
    public InvalidJWT(String message) {
        super(message);
    }
}
