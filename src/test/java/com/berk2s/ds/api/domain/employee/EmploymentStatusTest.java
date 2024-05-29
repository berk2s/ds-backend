package com.berk2s.ds.api.domain.employee;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EmploymentStatusTest {
    @Test
    public void shouldCreateEmploymentStatus() {
        // When
        EmploymentStatus status = EmploymentStatus.create();

        // Then
        assertNotNull(status);
        assertNotNull(status.getStartedAt());
        assertNull(status.getExitAt());
    }

    @Test
    public void shouldFired() {
        // Given
        EmploymentStatus status = EmploymentStatus.create();
        LocalDateTime beforeFired = LocalDateTime.now();

        // When
        status.fired();

        // Then
        assertNotNull(status.getExitAt());
        assertTrue(status.getExitAt().isAfter(beforeFired) || status.getExitAt().isEqual(beforeFired));
    }

}