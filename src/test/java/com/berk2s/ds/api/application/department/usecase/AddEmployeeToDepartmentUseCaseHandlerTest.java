package com.berk2s.ds.api.application.department.usecase;

import com.berk2s.ds.api.domain.department.Department;
import com.berk2s.ds.api.domain.department.DepartmentRepository;
import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddEmployeeToDepartmentUseCaseHandlerTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Department mockDepartment;

    @Mock
    private Employee mockEmployee;

    @InjectMocks
    private AddEmployeeToDepartmentUseCaseHandler handler;

    private Long departmentId;
    private UUID employeeId;

    @BeforeEach
    public void setUp() {
        departmentId = 1L;
        employeeId = UUID.randomUUID();
    }

    @Test
    public void testExecute() {
        AddEmployeeToDepartmentUseCase useCase = new AddEmployeeToDepartmentUseCase(departmentId, List.of(employeeId));

        when(departmentRepository.retrieve(departmentId)).thenReturn(mockDepartment);
        when(employeeRepository.retrieve(employeeId)).thenReturn(mockEmployee);
        when(departmentRepository.update(mockDepartment)).thenReturn(mockDepartment);

        Department updatedDepartment = handler.execute(useCase);

        verify(departmentRepository).retrieve(departmentId);
        verify(employeeRepository).retrieve(employeeId);
        verify(mockDepartment).addEmployee(mockEmployee);
        verify(departmentRepository).update(mockDepartment);

        assertEquals(mockDepartment, updatedDepartment);
    }
}