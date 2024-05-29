package com.berk2s.ds.api.domain.employee;

import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import com.berk2s.ds.api.domain.shared.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = PRIVATE)
public class Employee extends Entity {
    @EqualsAndHashCode.Include
    private UUID id;
    private EmployeeInformation information;
    private EmploymentStatus status;

    private Employee(UUID id,
                     EmployeeInformation information,
                     EmploymentStatus status) {
        this.id = id;
        this.information = information;
        this.status = status;

        validateAttached();
    }

    private Employee(EmployeeInformation information) {
        this.information = information;
        this.status = EmploymentStatus.create();

        validateCreated();
    }

    public void fired() {
        log.info("Employee has been fired.");

        status.fired();

        registerEvent(new EmployeeFiredEvent(id.toString()));
    }

    public static Employee create(EmployeeInformation employeeInformation) {
        return new Employee(employeeInformation);
    }

    public static Employee attach(UUID id,
                                  EmployeeInformation employeeInformation,
                                  EmploymentStatus employmentStatus) {
        return new Employee(id, employeeInformation, employmentStatus);
    }

    private void validateAttached() {
        if (Objects.isNull(id)) {
            log.warn("Employee ID is null.");
            throw new DomainRuleViolationException("employee.id.null");
        }

        validateCreated();
    }

    private void validateCreated() {
        if (Objects.isNull(information)) {
            log.warn("Employee information is null.");
            throw new DomainRuleViolationException("employee.information.null");
        }

        if (Objects.isNull(status)) {
            log.warn("Employee status is null.");
            throw new DomainRuleViolationException("employee.status.null");
        }
    }
}
