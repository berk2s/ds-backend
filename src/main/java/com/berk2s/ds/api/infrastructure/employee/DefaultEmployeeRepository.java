package com.berk2s.ds.api.infrastructure.employee;

import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.domain.employee.EmployeeRepository;
import com.berk2s.ds.api.infrastructure.common.ResourceNotFoundException;
import com.berk2s.ds.api.infrastructure.employee.persistence.EmployeeEntity;
import com.berk2s.ds.api.infrastructure.employee.persistence.EmployeeJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Employee update(Employee employee) {
        var entity = getById(employee.getId());
        entity.setFirstName(employee.getInformation().getFirstName());
        entity.setLastName(employee.getInformation().getLastName());

        if (!entity.getPhoneNumber().equals(employee.getInformation().getPhoneNumber()) && !isPhoneNumberTaken(employee.getInformation().getPhoneNumber())) {
            entity.setPhoneNumber(employee.getInformation().getPhoneNumber());
        }

        entity.setExitAt(employee.getStatus().getExitAt());

        employeeJPARepository.save(entity);

        log.info("Employee is updated. [id: {}]", employee.getId().toString());

        return entity.toModel();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Employee retrieve(UUID id) {
        return getById(id).toModel();
    }

    private EmployeeEntity getById(UUID id) {
        return employeeJPARepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee with the given id does not exists. [id: {}]", id.toString());
                    return new ResourceNotFoundException("employee.notFound");
                });
    }
}
