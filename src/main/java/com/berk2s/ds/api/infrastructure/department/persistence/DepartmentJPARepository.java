package com.berk2s.ds.api.infrastructure.department.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentJPARepository extends PagingAndSortingRepository<DepartmentEntity, Long>, CrudRepository<DepartmentEntity, Long> {
}
