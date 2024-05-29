package com.berk2s.ds.api.application.employee.usecase;

import com.berk2s.ds.api.application.shared.UseCaseHandler;
import com.berk2s.ds.api.application.shared.UseCaseHandlerService;
import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@UseCaseHandlerService
public class DeleteEmployeeUseCaseHandler implements UseCaseHandler<Employee, DeleteEmployeeUseCase> {
    private final EmployeeRepository employeeRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Employee execute(DeleteEmployeeUseCase useCase) {
        var employee = employeeRepository
                .retrieve(useCase.getEmployeeId());

        employeeRepository.delete(employee);

        return employee;
    }
}
