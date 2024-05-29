package com.berk2s.ds.api.infrastructure.employee;

import com.berk2s.ds.api.infrastructure.common.ResourceNotFoundException;
import com.berk2s.ds.api.infrastructure.employee.dto.EmployeeResponse;
import com.berk2s.ds.api.infrastructure.employee.persistence.EmployeeJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeFacade {
    private final EmployeeJPARepository employeeJPARepository;

    public Page<EmployeeResponse> getEmployees(Pageable pageable) {
        return employeeJPARepository
                .findAll(pageable)
                .map(EmployeeResponse::fromEntity);
    }

    public EmployeeResponse getEmployeeById(UUID id) {
        return EmployeeResponse.fromEntity(employeeJPARepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Given employee with the id does not exists. [id: {}]", id);
                    return new ResourceNotFoundException("employee.notFound");
                }));
    }
}
