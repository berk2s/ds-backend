package com.berk2s.ds.api.application.employee.usecase;

import com.berk2s.ds.api.application.shared.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@AllArgsConstructor
@Builder
@Getter
public class UpdateEmployeeUseCase implements UseCase {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
