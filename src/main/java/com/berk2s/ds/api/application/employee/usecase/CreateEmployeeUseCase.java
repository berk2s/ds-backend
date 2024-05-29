package com.berk2s.ds.api.application.employee.usecase;

import com.berk2s.ds.api.application.shared.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Builder
@Getter
public class CreateEmployeeUseCase implements UseCase {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
