package com.berk2s.ds.api.domain.department;

import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import com.berk2s.ds.api.domain.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = PRIVATE)
public class DepartmentInformation implements ValueObject<DepartmentInformation> {
    @EqualsAndHashCode.Include
    private String displayName;
    @EqualsAndHashCode.Include
    private DepartmentType type;

    private DepartmentInformation(String displayName,
                                  DepartmentType type) {
        this.displayName = displayName;
        this.type = type;

        validate();
    }

    public static DepartmentInformation create(String displayName,
    DepartmentType type) {
        return new DepartmentInformation(displayName, type);
    }

    @Override
    public boolean sameValueAs(DepartmentInformation other) {
        return Objects.equals(other.getDisplayName(), displayName) && other.getType() == type;
    }

    private void validate() {
        if (Objects.isNull(displayName)) {
            log.warn("Department display name is null.");
            throw new DomainRuleViolationException("department.displayName.null");
        }

        if (Objects.isNull(type)) {
            log.warn("Department type is null");
            throw new DomainRuleViolationException("department.type.null");
        }
    }
}
