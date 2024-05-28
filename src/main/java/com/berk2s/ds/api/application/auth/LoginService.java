package com.berk2s.ds.api.application.auth;

public interface LoginService {
    String login(String username, String password);
    void logout();
}
