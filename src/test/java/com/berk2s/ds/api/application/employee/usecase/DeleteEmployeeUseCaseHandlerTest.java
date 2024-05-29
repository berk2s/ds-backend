package com.berk2s.ds.api.application.employee.usecase;

import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeInformation;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
import com.berk2s.ds.api.infrastructure.common.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteEmployeeUseCaseHandlerTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    DeleteEmployeeUseCaseHandler handler;

    private DeleteEmployeeUseCase useCase;
    private Employee existingEmployee;

    @BeforeEach
    void setUp() {
        existingEmployee = Employee.create(
                EmployeeInformation.create("FirstName", "LastName", "1234567890")
        );
        useCase = new DeleteEmployeeUseCase(existingEmployee.getId());
    }

    @Test
    void shouldDeleteEmployeeSuccessfully() {
        // Given
        when(employeeRepository.retrieve(useCase.getEmployeeId())).thenReturn(existingEmployee);

        // When
        Employee result = handler.execute(useCase);

        // Then
        assertNotNull(result);
        assertEquals(existingEmployee.getId(), result.getId());
        verify(employeeRepository, times(1)).retrieve(useCase.getEmployeeId());
        verify(employeeRepository, times(1)).delete(existingEmployee);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        // Given
        when(employeeRepository.retrieve(useCase.getEmployeeId())).thenThrow(new ResourceNotFoundException("Employee not found"));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            handler.execute(useCase);
        });

        assertEquals("Employee not found", exception.getMessage());
        verify(employeeRepository, times(1)).retrieve(useCase.getEmployeeId());
        verify(employeeRepository, never()).delete(any(Employee.class));
    }
}