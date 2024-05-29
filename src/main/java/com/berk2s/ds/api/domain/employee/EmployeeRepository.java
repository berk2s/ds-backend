package com.berk2s.ds.api.domain.employee;

public interface EmployeeRepository {
    boolean isPhoneNumberTaken(String phoneNumber);
    Employee save(Employee employee);
}
