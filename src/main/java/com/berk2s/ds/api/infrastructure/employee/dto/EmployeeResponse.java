package com.berk2s.ds.api.infrastructure.employee.dto;

import com.berk2s.ds.api.domain.employee.Employee;
import com.berk2s.ds.api.infrastructure.employee.persistence.EmployeeEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class EmployeeResponse extends RepresentationModel<EmployeeResponse> {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static EmployeeResponse fromModel(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId().toString())
                .firstName(employee.getInformation().getFirstName())
                .lastName(employee.getInformation().getLastName())
                .phoneNumber(employee.getInformation().getPhoneNumber())
                .createdAt(employee.getLifecycleDate().getCreatedAt())
                .lastModifiedAt(employee.getLifecycleDate().getLastModifiedAt())
                .build();
    }

    public static EmployeeResponse fromEntity(EmployeeEntity employee) {
        return EmployeeResponse.builder()
                .id(employee.getId().toString())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .phoneNumber(employee.getPhoneNumber())
                .createdAt(LocalDateTime.from(employee.getCreatedAt()))
                .lastModifiedAt(LocalDateTime.from(employee.getLastModifiedAt()))
                .build();
    }
}
