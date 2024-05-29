package com.berk2s.ds.api.domain.employee;

import java.util.UUID;

public interface EmployeeRepository {
    boolean isPhoneNumberTaken(String phoneNumber);
    Employee save(Employee employee);
    Employee update(Employee employee);
    Employee retrieve(UUID id);
}
