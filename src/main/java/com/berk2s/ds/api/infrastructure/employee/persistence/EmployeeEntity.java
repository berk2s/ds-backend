package com.berk2s.ds.api.infrastructure.employee.persistence;

import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeInformation;
import com.berk2s.ds.api.domain.employee.EmploymentStatus;
import com.berk2s.ds.api.domain.shared.LifecycleDate;
import com.berk2s.ds.api.infrastructure.persistence.LongIdentifierEntity;
import com.berk2s.ds.api.infrastructure.persistence.UUIDIdentifierEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    public Employee toModel() {
        return Employee.attach(getId(), EmployeeInformation.create(firstName, lastName, phoneNumber), EmploymentStatus.create(LocalDateTime.from(getCreatedAt()), exitAt),
                LifecycleDate.create(LocalDateTime.from(getCreatedAt()), LocalDateTime.from(getLastModifiedAt())));
    }
}
