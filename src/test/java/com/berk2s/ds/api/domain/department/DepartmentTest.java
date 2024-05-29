package com.berk2s.ds.api.domain.department;

import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeInformation;
import com.berk2s.ds.api.domain.employee.EmploymentStatus;
import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentTest {
    private DepartmentInformation information;
    private DepartmentQuota quota;
    private List<Employee> employees;
    private Employee employee;

    @BeforeEach
    void setUp() {
        // Given
        information = DepartmentInformation.create(RandomStringUtils.randomAlphabetic(5), DepartmentType.IT);
        quota = DepartmentQuota.create(100L);
        employees = new ArrayList<>();
        EmployeeInformation employeeInformation = EmployeeInformation.create(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(9));
        employee = Employee.attach(UUID.randomUUID(), employeeInformation, EmploymentStatus.create());
    }

    @Test
    public void shouldCreateDepartmentSuccessfully() {
        // When
        Department department = Department.create(information, quota, employees);

        // Then
        assertNotNull(department);
        assertEquals(information, department.getInformation());
        assertEquals(quota, department.getQuota());
        assertEquals(employees, department.getEmployees());
    }

    @Test
    public void shouldAttachDepartmentSuccessfully() {
        // Given
        Long id = 1L;

        // When
        Department department = Department.attach(id, information, quota, employees);

        // Then
        assertNotNull(department);
        assertEquals(id, department.getId());
        assertEquals(information, department.getInformation());
        assertEquals(quota, department.getQuota());
        assertEquals(employees, department.getEmployees());
    }

    @Test
    public void shouldAddEmployeeSuccessfully() {
        // Given
        Department department = Department.create(information, quota, employees);

        // When
        department.addEmployee(employee);

        // Then
        assertTrue(department.getEmployees().contains(employee));
        assertTrue(department.pickDomainEvents().contains(new EmployeeRegisteredToDepartmentEvent(department.getId(), employee.getId().toString())));
    }

    @Test
    public void shouldFailWhenEmployeeAlreadyExists() {
        // Given
        Department department = Department.create(information, quota, employees);
        employees.add(employee);

        // When
        DomainRuleViolationException exception = assertThrows(DomainRuleViolationException.class, () -> {
            department.addEmployee(employee);
        });

        assertEquals("department.employee.alreadyExists", exception.getMessage());
    }

    @Test
    public void shouldRemoveEmployeeSuccessfully() {
        // Given
        employees.add(employee);
        Department department = Department.attach(1L, information, quota, employees);

        // When
        department.removeEmployee(employee);

        // Then
        assertFalse(department.getEmployees().contains(employee));
        assertTrue(department.pickDomainEvents().contains(new EmployeeRemovedFromDepartmentEvent(department.getId(), employee.getId().toString())));
    }

    @Test
    public void testQuotaAvailability() {
        // Given
        Department department = Department.create(information, DepartmentQuota.create(1L), employees);
        employees.add(employee);

        // When
        DomainRuleViolationException exception = assertThrows(DomainRuleViolationException.class, () -> {
            department.checkQuotaAvailability();
        });

        // Then
        assertEquals("department.inMaximumCapacity", exception.getMessage());
    }

}