package com.berk2s.ds.api.infrastructure.employee.persistence;

import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultEmployeeRepository implements EmployeeRepository {
    private final EmployeeJPARepository employeeJPARepository;

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public boolean isPhoneNumberTaken(String phoneNumber) {
        return employeeJPARepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Employee save(Employee employee) {
        var employeeEntity = new EmployeeEntity();
        employeeEntity.setFirstName(employee.getInformation().getFirstName());
        employeeEntity.setLastName(employee.getInformation().getLastName());
        employeeEntity.setPhoneNumber(employee.getInformation().getPhoneNumber());

        return employeeJPARepository.save(employeeEntity).toModel();
    }
}
