package com.berk2s.ds.api.domain.employee;

import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeInformationTest {

    @Test
    public void shouldCreateValidEmployeeInformation() {
        // When
        EmployeeInformation info = EmployeeInformation.create("Test", "test", "1234567890");

        // Then
        assertNotNull(info);
        assertEquals("Test", info.getFirstName());
        assertEquals("test", info.getLastName());
        assertEquals("1234567890", info.getPhoneNumber());
    }

    @Test
    public void shouldCreateEmployeeInformationWithNullFirstName() {
        // When
        DomainRuleViolationException thrown = assertThrows(
                DomainRuleViolationException.class,
                () -> EmployeeInformation.create(null, "test", "1234567890")
        );

        // Then
        assertEquals("employee.firstName.null", thrown.getMessage());
    }

    @Test
    public void shouldCreateEmployeeInformationWithNullLastName() {
        // When
        DomainRuleViolationException thrown = assertThrows(
                DomainRuleViolationException.class,
                () -> EmployeeInformation.create("Test", null, "1234567890")
        );

        // Then
        assertEquals("employee.lastName.null", thrown.getMessage());
    }

    @Test
    public void shouldCreateEmployeeInformationWithNullPhoneNumber() {
        // When
        DomainRuleViolationException thrown = assertThrows(
                DomainRuleViolationException.class,
                () -> EmployeeInformation.create("Test", "test", null)
        );

        // Then
        assertEquals("employee.phoneNumber.null", thrown.getMessage());
    }
}