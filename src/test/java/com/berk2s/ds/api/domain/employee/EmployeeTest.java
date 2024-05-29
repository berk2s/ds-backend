package com.berk2s.ds.api.domain.employee;

import com.berk2s.ds.api.domain.department.EmployeeRemovedFromDepartmentEvent;
import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    @Test
    public void shouldCreateValidEmployee() {
        // Given
        EmployeeInformation information = EmployeeInformation.create(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));

        // When
        Employee employee = Employee.create(information);

        // Then
        assertNotNull(employee);
        assertEquals(information, employee.getInformation());
        assertNotNull(employee.getStatus());
    }

    @Test
    public void shouldAttachValidEmployee() {
        // Given
        UUID id = UUID.randomUUID();
        EmployeeInformation information = EmployeeInformation.create(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        EmploymentStatus status = EmploymentStatus.create();

        // When
        Employee employee = Employee.attach(id, information, status);

        // Then
        assertNotNull(employee);
        assertEquals(id, employee.getId());
        assertEquals(information, employee.getInformation());
        assertEquals(status, employee.getStatus());
    }

    @Test
    public void shouldCreateEmployeeWithNullInformation() {
        DomainRuleViolationException thrown = assertThrows(
                DomainRuleViolationException.class,
                () -> Employee.create(null)
        );

        assertEquals("employee.information.null", thrown.getMessage());
    }

    @Test
    public void shouldAttachEmployeeWithNullId() {
        // Given
        EmployeeInformation information = EmployeeInformation.create(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        EmploymentStatus status = EmploymentStatus.create();

        // When
        DomainRuleViolationException thrown = assertThrows(
                DomainRuleViolationException.class,
                () -> Employee.attach(null, information, status)
        );

        // Then
        assertEquals("employee.id.null", thrown.getMessage());
    }

    @Test
    public void shouldAttachEmployeeWithNullInformation() {
        // Given
        EmploymentStatus status = EmploymentStatus.create();

        UUID id = UUID.randomUUID();

        // When
        DomainRuleViolationException thrown = assertThrows(
                DomainRuleViolationException.class,
                () -> Employee.attach(id, null, status)
        );

        // Then
        assertEquals("employee.information.null", thrown.getMessage());
    }

    @Test
    public void shouldAttachEmployeeWithNullStatus() {
        // Given
        EmployeeInformation information = EmployeeInformation.create(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        UUID id = UUID.randomUUID();

        // When
        DomainRuleViolationException thrown = assertThrows(
                DomainRuleViolationException.class,
                () -> Employee.attach(id, information, null)
        );

        // Then
        assertEquals("employee.status.null", thrown.getMessage());
    }

    @Test
    public void testFired() {
        // Given
        UUID id = UUID.randomUUID();
        EmployeeInformation information = EmployeeInformation.create(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        EmploymentStatus status = EmploymentStatus.create();
        Employee employee = Employee.attach(id, information, status);

        // When
        employee.fired();

        // Then
        assertTrue(employee.pickDomainEvents().contains(new EmployeeFiredEvent(id.toString())));
    }
}