package com.berk2s.ds.api.infrastructure.employee.persistence;

import com.berk2s.ds.api.domain.department.Department;
import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeDepartment;
import com.berk2s.ds.api.domain.employee.EmployeeInformation;
import com.berk2s.ds.api.domain.employee.EmploymentStatus;
import com.berk2s.ds.api.domain.shared.LifecycleDate;
import com.berk2s.ds.api.infrastructure.department.persistence.DepartmentEntity;
import com.berk2s.ds.api.infrastructure.persistence.LongIdentifierEntity;
import com.berk2s.ds.api.infrastructure.persistence.UUIDIdentifierEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "employees", indexes = {
        @Index(name = "fn_phone_number", columnList = "phone_number", unique = true),
})
public class EmployeeEntity extends UUIDIdentifierEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "exit_at", nullable = true)
    private LocalDateTime exitAt;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = true)
    private DepartmentEntity department;

    public Employee toModel() {
        return Employee.attach(
                getId(),
                EmployeeInformation.create(firstName, lastName, phoneNumber),
                EmploymentStatus.create(LocalDateTime.from(getCreatedAt()), exitAt),
                Objects.isNull(department) ? null : EmployeeDepartment.create(department.getId(), department.getDisplayName()),
                LifecycleDate.create(LocalDateTime.from(getCreatedAt()), LocalDateTime.from(getLastModifiedAt())));
    }
}
