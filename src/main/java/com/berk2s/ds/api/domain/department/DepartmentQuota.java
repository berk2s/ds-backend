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
public class DepartmentQuota implements ValueObject<DepartmentQuota> {
    @EqualsAndHashCode.Include
    private Long maximumMember;

    private DepartmentQuota(Long maximumMember) {
        this.maximumMember = maximumMember;

        validate();
    }

    public static DepartmentQuota create(Long maximumMember) {
        return new DepartmentQuota(maximumMember);
    }

    @Override
    public boolean sameValueAs(DepartmentQuota other) {
        return Objects.equals(other.getMaximumMember(), maximumMember);
    }

    private void validate() {
        if (Objects.isNull(maximumMember)) {
            log.warn("Department quota maximum member is null.");
            throw new DomainRuleViolationException("department.maximumMember.null");
        }
    }
}
