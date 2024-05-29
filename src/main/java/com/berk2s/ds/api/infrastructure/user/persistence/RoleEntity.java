package com.berk2s.ds.api.infrastructure.user.persistence;

import com.berk2s.ds.api.infrastructure.persistence.LongIdentifierEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_roles", indexes = {
        @Index(name = "fn_role_name", columnList = "display_name", unique = true),
})
public class RoleEntity extends LongIdentifierEntity {

    @Column(name = "display_name", unique = true)
    private String displayName;
}
