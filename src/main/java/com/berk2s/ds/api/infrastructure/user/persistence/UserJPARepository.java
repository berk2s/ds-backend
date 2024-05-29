package com.berk2s.ds.api.infrastructure.user.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJPARepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
