package com.berk2s.ds.api.infrastructure.department;

import com.berk2s.ds.api.domain.department.Department;
import com.berk2s.ds.api.domain.department.DepartmentRepository;
import com.berk2s.ds.api.infrastructure.common.ResourceNotFoundException;
import com.berk2s.ds.api.infrastructure.department.persistence.DepartmentEntity;
import com.berk2s.ds.api.infrastructure.department.persistence.DepartmentJPARepository;
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
public class DefaultDepartmentRepository implements DepartmentRepository {
    private final DepartmentJPARepository departmentJPARepository;
    private final EmployeeJPARepository employeeJPARepository;

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public Department save(Department department) {
        var departmentEntity = new DepartmentEntity();
        departmentEntity.setDisplayName(department.getInformation().getDisplayName());
        departmentEntity.setMaximumMember(department.getQuota().getMaximumMember());
        departmentEntity.setType(department.getInformation().getType());

        department
                .getEmployees()
                .forEach((employee -> {
                    var employeeEntity = employeeById(employee.getId());
                    departmentEntity.addEmployee(employeeEntity);
                }));

        return departmentJPARepository.save(departmentEntity).toModel();
    }

    private EmployeeEntity employeeById(UUID id) {
        return employeeJPARepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Given employee with the id does not exists. [id: {}]", id);
                    return new ResourceNotFoundException("employee.notFound");
                });
    }
}
