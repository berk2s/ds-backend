package com.berk2s.ds.api.infrastructure.employee.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface EmployeeJPARepository extends PagingAndSortingRepository<EmployeeEntity, UUID>, CrudRepository<EmployeeEntity, UUID> {
    boolean existsByPhoneNumber(String phoneNumber);
}
