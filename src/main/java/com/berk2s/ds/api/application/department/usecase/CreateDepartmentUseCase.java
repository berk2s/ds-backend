package com.berk2s.ds.api.application.department.usecase;

import com.berk2s.ds.api.application.shared.UseCase;
import com.berk2s.ds.api.domain.department.DepartmentType;
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
public class CreateDepartmentUseCase implements UseCase {
    private String displayName;
    private DepartmentType departmentType;
    private Long maximumMember;
    private List<UUID> employees;
}
