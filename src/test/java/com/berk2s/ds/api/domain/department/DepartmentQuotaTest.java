package com.berk2s.ds.api.domain.department;

import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentQuotaTest {
    @Test
    public void testCreateValidDepartmentQuota() {
        DepartmentQuota quota = DepartmentQuota.create(10L);
        assertNotNull(quota);
        assertEquals(10L, quota.getMaximumMember());
    }

    @Test
    public void testCreateDepartmentQuotaWithNullMaximumMember() {
        DomainRuleViolationException thrown = assertThrows(
                DomainRuleViolationException.class,
                () -> DepartmentQuota.create(null)
        );

        assertEquals("department.maximumMember.null", thrown.getMessage());
    }
}