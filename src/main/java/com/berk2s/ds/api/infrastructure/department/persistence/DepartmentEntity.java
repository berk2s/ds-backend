package com.berk2s.ds.api.infrastructure.department.persistence;

import com.berk2s.ds.api.domain.department.Department;
import com.berk2s.ds.api.domain.department.DepartmentInformation;
import com.berk2s.ds.api.domain.department.DepartmentQuota;
import com.berk2s.ds.api.domain.department.DepartmentType;
import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.shared.LifecycleDate;
import com.berk2s.ds.api.infrastructure.employee.persistence.EmployeeEntity;
import com.berk2s.ds.api.infrastructure.persistence.LongIdentifierEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class DepartmentEntity extends LongIdentifierEntity {
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "department_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DepartmentType type;

    @Column(name = "maximum_member_number", nullable = false)
    private Long maximumMember;

    @OneToMany(mappedBy = "department", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<EmployeeEntity> employees = new HashSet<>();

    public void addEmployee(EmployeeEntity entity) {
        employees.add(entity);
    }

    public void removeEmployee(EmployeeEntity entity) {
        employees.remove(entity);
    }

    public Department toModel() {
        return Department
                .attach(
                        getId(),
                        DepartmentInformation.create(displayName, type),
                        DepartmentQuota.create(maximumMember),
                        employees
                                .stream().map((i) -> i.toModel())
                                .collect(Collectors.toList()),
                        LifecycleDate.create(getCreatedAt(), getLastModifiedAt())
                );
    }
}
