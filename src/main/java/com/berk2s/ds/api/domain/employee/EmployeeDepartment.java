package com.berk2s.ds.api.domain.employee;

import com.berk2s.ds.api.domain.shared.Entity;
import com.berk2s.ds.api.domain.shared.ValueObject;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
public class EmployeeDepartment extends Entity {
    @EqualsAndHashCode.Include
    private Long id;
    private String displayName;

    public static EmployeeDepartment create(Long id,
                                            String displayName) {
        return new EmployeeDepartment(id, displayName);
    }
}
