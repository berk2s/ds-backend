package com.berk2s.ds.api.infrastructure.user.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleJPARepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByDisplayName(String displayName);
    boolean existsByDisplayName(String displayName);
}
