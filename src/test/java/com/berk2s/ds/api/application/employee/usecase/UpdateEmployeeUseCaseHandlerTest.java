package com.berk2s.ds.api.application.employee.usecase;

import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeInformation;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
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
class UpdateEmployeeUseCaseHandlerTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    UpdateEmployeeUseCaseHandler handler;

    private UpdateEmployeeUseCase useCase;
    private Employee existingEmployee;

    @BeforeEach
    void setUp() {
        existingEmployee = Employee.create(
                EmployeeInformation.create("FirstName", "LastName", "1234567890")
        );
        useCase = new UpdateEmployeeUseCase(existingEmployee.getId(), "FirstName1", "LastName1", "0987654321");
    }

    @Test
    void shouldUpdateEmployeeSuccessfully() {
        // Given
        when(employeeRepository.retrieve(useCase.getEmployeeId())).thenReturn(existingEmployee);
        when(employeeRepository.isPhoneNumberTaken(anyString())).thenReturn(false);
        when(employeeRepository.update(any(Employee.class))).thenReturn(existingEmployee);

        // When
        Employee result = handler.execute(useCase);

        // Then
        assertNotNull(result);
        assertEquals("FirstName1", result.getInformation().getFirstName());
        assertEquals("LastName1", result.getInformation().getLastName());
        assertEquals("0987654321", result.getInformation().getPhoneNumber());
        verify(employeeRepository, times(1)).retrieve(useCase.getEmployeeId());
        verify(employeeRepository, times(1)).update(any(Employee.class));
    }

}