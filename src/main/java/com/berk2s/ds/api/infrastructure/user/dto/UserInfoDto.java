package com.berk2s.ds.api.infrastructure.user.dto;

import com.berk2s.ds.api.infrastructure.user.jpa.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserInfoDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private Instant createdAt;
    private Instant lastModifiedAt;

    public static UserInfoDto fromEntity(UserEntity userEntity) {
        return UserInfoDto.builder()
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
