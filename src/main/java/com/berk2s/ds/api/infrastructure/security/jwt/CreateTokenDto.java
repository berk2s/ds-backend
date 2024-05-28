package com.berk2s.ds.api.infrastructure.security.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTokenDto {
    private String username;
    private long userId;
}
