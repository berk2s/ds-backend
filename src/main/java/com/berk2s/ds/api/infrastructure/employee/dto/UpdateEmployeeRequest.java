package com.berk2s.ds.api.infrastructure.employee.dto;

import com.berk2s.ds.api.application.employee.usecase.CreateEmployeeUseCase;
import com.berk2s.ds.api.application.employee.usecase.UpdateEmployeeUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public UpdateEmployeeUseCase toUseCase(UUID id) {
        return UpdateEmployeeUseCase.builder()
                .employeeId(id)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .build();
    }
}
