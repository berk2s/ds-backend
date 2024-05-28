package com.berk2s.ds.api.application.auth;

public interface AuthenticationService {
    boolean authenticate(String username, String password);
}
