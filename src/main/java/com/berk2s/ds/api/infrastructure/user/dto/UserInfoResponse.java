package com.berk2s.ds.api.infrastructure.user.dto;

import com.berk2s.ds.api.infrastructure.user.persistence.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
public class UserInfoResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static UserInfoResponse fromEntity(UserEntity userEntity) {
        return UserInfoResponse.builder()
                .userId(userEntity.getId().toString())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .username(userEntity.getUsername())
                .role(userEntity.getRole().getDisplayName())
                .createdAt(userEntity.getCreatedAt())
                .lastModifiedAt(userEntity.getLastModifiedAt())
                .build();
    }
}
