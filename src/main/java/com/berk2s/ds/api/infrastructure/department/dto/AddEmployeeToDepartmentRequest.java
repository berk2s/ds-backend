package com.berk2s.ds.api.infrastructure.department.dto;

import com.berk2s.ds.api.application.department.usecase.AddEmployeeToDepartmentUseCase;
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
public class AddEmployeeToDepartmentRequest {
    @NotNull(message = "employees.null")
    private List<UUID> employees;

    public AddEmployeeToDepartmentUseCase toUseCase(Long depmartmentId) {
        return AddEmployeeToDepartmentUseCase.builder()
                .departmentId(depmartmentId)
                .employees(employees)
                .build();
    }
}
