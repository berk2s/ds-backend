package com.berk2s.ds.api.domain.department;

import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentInformationTest {
    @Test
    public void shouldCreateValidDepartmentInformation() {
        DepartmentInformation deptInfo = DepartmentInformation.create("HR", DepartmentType.IT);
        assertNotNull(deptInfo);
        assertEquals("HR", deptInfo.getDisplayName());
        assertEquals(DepartmentType.IT, deptInfo.getType());
    }

    @Test
    public void shouldCreateDepartmentInformationWithNullDisplayName() {
        DomainRuleViolationException thrown = assertThrows(
                DomainRuleViolationException.class,
                () -> DepartmentInformation.create(null, DepartmentType.IT)
        );

        assertEquals("department.displayName.null", thrown.getMessage());
    }

    @Test
    public void shouldCreateDepartmentInformationWithNullType() {
        DomainRuleViolationException thrown = assertThrows(
                DomainRuleViolationException.class,
                () -> DepartmentInformation.create("HR", null)
        );

        assertEquals("department.type.null", thrown.getMessage());
    }
}