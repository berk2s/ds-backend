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

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@UseCaseHandlerService
public class UpdateEmployeeUseCaseHandler implements UseCaseHandler<Employee, UpdateEmployeeUseCase> {
    private final EmployeeRepository employeeRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @Override
    public Employee execute(UpdateEmployeeUseCase useCase) {
        var employee = employeeRepository
                .retrieve(useCase.getEmployeeId());

        if (!Objects.isNull(useCase.getFirstName()))
            employee.getInformation().setFirstName(useCase.getFirstName());

        if (!Objects.isNull(useCase.getLastName()))
            employee.getInformation().setLastName(useCase.getLastName());

        if (!Objects.isNull(useCase.getPhoneNumber()) && !employeeRepository.isPhoneNumberTaken(useCase.getPhoneNumber())) {
            employee.getInformation().setPhoneNumber(useCase.getPhoneNumber());
        }

        return employeeRepository.update(employee);
    }
}
