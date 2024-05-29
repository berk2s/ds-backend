package com.berk2s.ds.api.application.department.usecase;

import com.berk2s.ds.api.application.shared.UseCaseHandler;
import com.berk2s.ds.api.application.shared.UseCaseHandlerService;
import com.berk2s.ds.api.domain.department.Department;
import com.berk2s.ds.api.domain.department.DepartmentInformation;
import com.berk2s.ds.api.domain.department.DepartmentQuota;
import com.berk2s.ds.api.domain.department.DepartmentRepository;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@UseCaseHandlerService
public class CreateDepartmentUseCaseHandler implements UseCaseHandler<Department, CreateDepartmentUseCase> {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Department execute(CreateDepartmentUseCase useCase) {
        var information = DepartmentInformation.create(
                useCase.getDisplayName(),
                useCase.getDepartmentType()
        );
        var quota = DepartmentQuota.create(
                useCase.getMaximumMember()
        );

        if (useCase.getEmployees().size() > quota.getMaximumMember()) {
            log.info("The department has been reached to the maximum capacity");
            throw new DomainRuleViolationException("department.inMaximumCapacity");
        }

        var employees = useCase
                .getEmployees()
                .stream()
                .map(employeeRepository::retrieve)
                .collect(Collectors.toList());

        var department = Department.create(information, quota, employees);

        return departmentRepository.save(department);
    }
}
