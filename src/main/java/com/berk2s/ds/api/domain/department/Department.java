package com.berk2s.ds.api.domain.department;

import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.shared.Aggregate;
import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import com.berk2s.ds.api.domain.shared.LifecycleDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = PRIVATE)
public class Department extends Aggregate {
    @EqualsAndHashCode.Include
    private Long id;
    private DepartmentInformation information;
    private DepartmentQuota quota;
    private List<Employee> employees;
    private LifecycleDate lifecycleDate;

    private Department(DepartmentInformation information,
                       DepartmentQuota quota,
                       List<Employee> employees) {
        this.information = information;
        this.quota = quota;
        this.employees = employees;

        validateCreated();
    }

    private Department(Long id,
                       DepartmentInformation information,
                       DepartmentQuota quota,
                       List<Employee> employees) {
        this.id = id;
        this.information = information;
        this.quota = quota;
        this.employees = employees;

        validateAttached();
    }

    private Department(Long id,
                       DepartmentInformation information,
                       DepartmentQuota quota,
                       List<Employee> employees,
                       LifecycleDate lifecycleDate) {
        this.id = id;
        this.information = information;
        this.quota = quota;
        this.employees = employees;
        this.lifecycleDate = lifecycleDate;

        validateAttached();
    }

    public static Department attach(Long id,
                                    DepartmentInformation information,
                                    DepartmentQuota quota,
                                    List<Employee> employees) {
        return new Department(id, information, quota, employees);
    }

    public static Department attach(Long id,
                                    DepartmentInformation information,
                                    DepartmentQuota quota,
                                    List<Employee> employees,
                                    LifecycleDate lifecycleDate) {
        return new Department(id, information, quota, employees, lifecycleDate);
    }

    public static Department create(DepartmentInformation information,
                                    DepartmentQuota quota,
                                    List<Employee> employees) {
        return new Department(information, quota, employees);
    }

    public void checkQuotaAvailability() {
        if (employees.size() == quota.getMaximumMember()) {
            log.info("The department has been reached to the maximum capacity");
            throw new DomainRuleViolationException("department.inMaximumCapacity");
        }
    }

    public void addEmployee(Employee employee) {
        checkQuotaAvailability();

        if (employees.contains(employee)) {
            log.info("Given employee already exists in the department. [department: {}, employee: {}]", id, employee.getId());
            throw new DomainRuleViolationException("department.employee.alreadyExists");
        }

        employees.add(employee);

        registerEvent(new EmployeeRegisteredToDepartmentEvent(id, employee.getId().toString()));
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);

        log.info("Employee has been removed from department. [department: {}. employee: {}]", id, employee.getId());

        registerEvent(new EmployeeRemovedFromDepartmentEvent(id, employee.getId().toString()));
    }

    private void validateAttached() {
        if (Objects.isNull(id)) {
            log.warn("Department id is null.");
            throw new DomainRuleViolationException("department.information.null");
        }

       validateCreated();
    }

    private void validateCreated() {
        if (Objects.isNull(information)) {
            log.warn("Department information is null.");
            throw new DomainRuleViolationException("department.information.null");
        }

        if (Objects.isNull(quota)) {
            log.warn("Department quota is null.");
            throw new DomainRuleViolationException("department.quota.null");
        }

        if (Objects.isNull(employees)) {
            log.warn("Department employees is null.");
            throw new DomainRuleViolationException("department.employees.null");
        }
    }
}
