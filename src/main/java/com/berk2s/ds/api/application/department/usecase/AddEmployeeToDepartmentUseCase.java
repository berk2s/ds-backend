package com.berk2s.ds.api.application.department.usecase;

import com.berk2s.ds.api.application.shared.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@Builder
@Getter
public class AddEmployeeToDepartmentUseCase implements UseCase {
    private Long departmentId;
    private List<UUID> employees;
}
