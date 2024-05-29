package com.berk2s.ds.api.application.employee.usecase;

import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeInformation;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEmployeeUseCaseHandlerTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    CreateEmployeeUseCaseHandler handler;

    private CreateEmployeeUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateEmployeeUseCase("FirstName", "LastName", "1234567890");
    }

    @Test
    void shouldCreateEmployeeSuccessfully() {
        // Given
        EmployeeInformation employeeInformation = EmployeeInformation.create("FirstName", "LastName", "1234567890");
        Employee employee = Employee.create(employeeInformation);

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeRepository.isPhoneNumberTaken(anyString())).thenReturn(false);

        // When
        Employee result = handler.execute(useCase);

        // Then
        assertNotNull(result);
        assertEquals("1234567890", result.getInformation().getPhoneNumber());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void shouldThrowExceptionWhenPhoneNumberIsTaken() {
        // Given
        when(employeeRepository.isPhoneNumberTaken(anyString())).thenReturn(true);

        // When & Then
        DomainRuleViolationException exception = assertThrows(DomainRuleViolationException.class, () -> {
            handler.execute(useCase);
        });

        assertEquals("employee.phoneNumber.taken", exception.getMessage());
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}