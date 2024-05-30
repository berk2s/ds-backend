package com.berk2s.ds.api.domain.department;

public interface DepartmentRepository {
    Department save(Department department);
    Department retrieve(Long id);
    Department update(Department department);
}
