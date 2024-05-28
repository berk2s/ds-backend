package com.berk2s.ds.api.infrastructure.user.jpa;

import com.berk2s.ds.api.infrastructure.persistence.LongIdentifierEntity;
import com.berk2s.ds.api.infrastructure.persistence.UUIDIdentifierEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_users", indexes = {
        @Index(name = "fn_username", columnList = "username", unique = true),
})
public class UserEntity extends UUIDIdentifierEntity {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_account_enabled")
    private Boolean isAccountEnabled;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private RoleEntity role;
}
