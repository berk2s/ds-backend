package com.berk2s.ds.api.infrastructure.department.dto;

import com.berk2s.ds.api.application.department.usecase.CreateDepartmentUseCase;
import com.berk2s.ds.api.domain.department.DepartmentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDepartmentRequest {
    @NotNull(message = "displayName.null")
    private String displayName;
    @NotNull(message = "departmentType.null")
    private DepartmentType departmentType;
    @NotNull(message = "maximumMember.null")
    private Long maximumMember;
    @NotNull(message = "employees.null")
    private List<UUID> employees;

    public CreateDepartmentUseCase toUseCase() {
        return CreateDepartmentUseCase.builder()
                .displayName(displayName)
                .departmentType(departmentType)
                .maximumMember(maximumMember)
                .employees(employees)
                .build();
    }
}
