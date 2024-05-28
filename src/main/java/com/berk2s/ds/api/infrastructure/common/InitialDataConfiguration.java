package com.berk2s.ds.api.infrastructure.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("initial-data")
@Getter
@Setter
public class InitialDataConfiguration {

    private List<UserInitialData> users;
    private List<RoleInitialData> roles;

    @Data
    @AllArgsConstructor
    public static class UserInitialData {
        private String username;
        private String firstName;
        private String lastName;
        private String password;
        private String role;
    }

    @Data
    @AllArgsConstructor
    public static class RoleInitialData {
        private String displayName;
    }
}