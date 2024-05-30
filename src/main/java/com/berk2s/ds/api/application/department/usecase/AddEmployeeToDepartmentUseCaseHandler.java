package com.berk2s.ds.api.application.department.usecase;

import com.berk2s.ds.api.application.shared.UseCaseHandler;
import com.berk2s.ds.api.application.shared.UseCaseHandlerService;
import com.berk2s.ds.api.domain.department.Department;
import com.berk2s.ds.api.domain.department.DepartmentRepository;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@UseCaseHandlerService
public class AddEmployeeToDepartmentUseCaseHandler implements UseCaseHandler<Department, AddEmployeeToDepartmentUseCase> {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Department execute(AddEmployeeToDepartmentUseCase useCase) {
        var department = departmentRepository
                .retrieve(useCase.getDepartmentId());

        useCase
                .getEmployees()
                .stream()
                .map(employeeRepository::retrieve)
                .forEach((department::addEmployee));

        return departmentRepository.update(department);
    }
}
