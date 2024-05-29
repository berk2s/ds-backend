package com.berk2s.ds.api.infrastructure.department.dto;

import com.berk2s.ds.api.domain.department.Department;
import com.berk2s.ds.api.domain.department.DepartmentType;
import com.berk2s.ds.api.infrastructure.department.persistence.DepartmentEntity;
import com.berk2s.ds.api.infrastructure.employee.dto.EmployeeResponse;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class DepartmentResponse extends RepresentationModel<DepartmentResponse> {
    private String id;
    private String displayName;
    private DepartmentType departmentType;
    private Long maximumMemberCapacity;
    private List<EmployeeResponse> employees;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static DepartmentResponse fromModel(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId().toString())
                .displayName(department.getInformation().getDisplayName())
                .departmentType(department.getInformation().getType())
                .maximumMemberCapacity(department.getQuota().getMaximumMember())
                .employees(department.getEmployees().stream().map(EmployeeResponse::fromModel).collect(Collectors.toList()))
                .createdAt(department.getLifecycleDate().getCreatedAt())
                .lastModifiedAt(department.getLifecycleDate().getLastModifiedAt())
                .build();
    }

    public static DepartmentResponse fromEntity(DepartmentEntity department) {
        return DepartmentResponse.builder()
                .id(department.getId().toString())
                .displayName(department.getDisplayName())
                .departmentType(department.getType())
                .maximumMemberCapacity(department.getMaximumMember())
                .employees(department.getEmployees().stream().map(EmployeeResponse::fromEntity).collect(Collectors.toList()))
                .createdAt(department.getLastModifiedAt())
                .lastModifiedAt(department.getLastModifiedAt())
                .build();
    }
}
