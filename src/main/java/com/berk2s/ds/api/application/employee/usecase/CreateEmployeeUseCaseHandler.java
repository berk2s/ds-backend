package com.berk2s.ds.api.application.employee.usecase;

import com.berk2s.ds.api.application.shared.UseCaseHandler;
import com.berk2s.ds.api.application.shared.UseCaseHandlerService;
import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeInformation;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@UseCaseHandlerService
public class CreateEmployeeUseCaseHandler implements UseCaseHandler<Employee, CreateEmployeeUseCase> {
    private final EmployeeRepository employeeRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Employee execute(CreateEmployeeUseCase useCase) {
        checkPhoneNumberTaken(useCase);

        var employeeInformation = EmployeeInformation.create(
                useCase.getFirstName(),
                useCase.getLastName(),
                useCase.getPhoneNumber()
        );

        var employee = Employee.create(employeeInformation);

        var savedEmployee = employeeRepository.save(employee);

        log.info("Employee has been created. [employee: {}]", employee.getId());

        return savedEmployee;
    }

    private void checkPhoneNumberTaken(CreateEmployeeUseCase useCase) {
        if (employeeRepository.isPhoneNumberTaken(useCase.getPhoneNumber())) {
            log.info("Given phone number for the employee is already exists. [phoneNumber: {}]", useCase.getPhoneNumber());
            throw new DomainRuleViolationException("employee.phoneNumber.taken");
        }
    }
}
