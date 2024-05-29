package com.berk2s.ds.api.infrastructure.employee.dto;

import com.berk2s.ds.api.application.employee.usecase.CreateEmployeeUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {
    @NotNull(message = "firstName.null")
    private String firstName;
    @NotNull(message = "lastName.null")
    private String lastName;
    @NotNull(message = "phoneNumber.null")
    private String phoneNumber;

    public CreateEmployeeUseCase toUseCase() {
        return CreateEmployeeUseCase.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .build();
    }
}
