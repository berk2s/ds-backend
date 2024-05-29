package com.berk2s.ds.api.application.department.usecase;

import com.berk2s.ds.api.domain.department.*;
import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeInformation;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
import com.berk2s.ds.api.domain.employee.EmploymentStatus;
import com.berk2s.ds.api.domain.shared.LifecycleDate;
import com.berk2s.ds.api.infrastructure.common.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateDepartmentUseCaseHandlerTest {
    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    CreateDepartmentUseCaseHandler handler;

    private CreateDepartmentUseCase useCase;
    private Employee employee1;
    private Employee employee2;
    private List<UUID> employeeIds;

    @BeforeEach
    void setUp() {
        employee1 = Employee.attach(
                UUID.randomUUID(),
                EmployeeInformation.create("Emp1", "emp1", "1234567890"),
                EmploymentStatus.create(),
                LifecycleDate.create(LocalDateTime.now(), LocalDateTime.now())
        );
        employee2 = Employee.attach(
                UUID.randomUUID(),
                EmployeeInformation.create("Emp2", "emp2", "0987654321"),
                EmploymentStatus.create(),
                LifecycleDate.create(LocalDateTime.now(), LocalDateTime.now())
        );

        employeeIds = List.of(employee1.getId(), employee2.getId());

        useCase = new CreateDepartmentUseCase(
                "Development",
                DepartmentType.IT,
                10L,
                employeeIds
        );
    }

    @Test
    void shouldCreateDepartmentSuccessfully() {
        // Given
        when(employeeRepository.retrieve(employee1.getId())).thenReturn(employee1);
        when(employeeRepository.retrieve(employee2.getId())).thenReturn(employee2);

        var departmentInformation = DepartmentInformation.create("Development", DepartmentType.IT);
        var departmentQuota = DepartmentQuota.create(10L);
        var employees = List.of(employee1, employee2);
        var department = Department.create(departmentInformation, departmentQuota, employees);

        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        // When
        Department result = handler.execute(useCase);

        // Then
        assertNotNull(result);
        assertEquals("Development", result.getInformation().getDisplayName());
        assertEquals(DepartmentType.IT, result.getInformation().getType());
        assertEquals(10, result.getQuota().getMaximumMember());
        assertEquals(2, result.getEmployees().size());
        verify(employeeRepository, times(1)).retrieve(employee1.getId());
        verify(employeeRepository, times(1)).retrieve(employee2.getId());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        // Given
        when(employeeRepository.retrieve(employee1.getId())).thenThrow(new ResourceNotFoundException("Employee not found"));

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            handler.execute(useCase);
        });

        assertEquals("Employee not found", exception.getMessage());
        verify(employeeRepository, times(1)).retrieve(employee1.getId());
        verify(employeeRepository, never()).retrieve(employee2.getId());
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void shouldThrowExceptionWhenSavingDepartmentFails() {
        // Given
        when(employeeRepository.retrieve(employee1.getId())).thenReturn(employee1);
        when(employeeRepository.retrieve(employee2.getId())).thenReturn(employee2);

        var departmentInformation = DepartmentInformation.create("Development", DepartmentType.IT);
        var departmentQuota = DepartmentQuota.create(10L);
        var employees = List.of(employee1, employee2);
        var department = Department.create(departmentInformation, departmentQuota, employees);

        when(departmentRepository.save(any(Department.class))).thenThrow(new RuntimeException("Failed to save department"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            handler.execute(useCase);
        });

        assertEquals("Failed to save department", exception.getMessage());
        verify(employeeRepository, times(1)).retrieve(employee1.getId());
        verify(employeeRepository, times(1)).retrieve(employee2.getId());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

}