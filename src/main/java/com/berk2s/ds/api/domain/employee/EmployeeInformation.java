package com.berk2s.ds.api.domain.employee;

import com.berk2s.ds.api.domain.shared.DomainRuleViolationException;
import com.berk2s.ds.api.domain.shared.ValueObject;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = PRIVATE)
public class EmployeeInformation implements ValueObject<EmployeeInformation> {
    @EqualsAndHashCode.Include
    private String firstName;
    @EqualsAndHashCode.Include
    private String lastName;
    @EqualsAndHashCode.Include
    private String phoneNumber;

    public EmployeeInformation(String firstName,
                               String lastName,
                               String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;

        validate();
    }

    public static EmployeeInformation create(String firstName,
                                             String lastName,
                                             String phoneNumber) {
        return new EmployeeInformation(firstName, lastName, phoneNumber);
    }

    @Override
    public boolean sameValueAs(EmployeeInformation other) {
        return firstName.equals(other.getFirstName()) && lastName.equals(other.getLastName()) && phoneNumber.equals(other.getPhoneNumber());
    }

    private void validate() {
        if (Objects.isNull(firstName)) {
            log.warn("First name is null.");
            throw new DomainRuleViolationException("employee.firstName.null");
        }

        if (Objects.isNull(lastName)) {
            log.warn("Last name is null.");
            throw new DomainRuleViolationException("employee.lastName.null");
        }

        if (Objects.isNull(phoneNumber)) {
            log.warn("Phone number is null.");
            throw new DomainRuleViolationException("employee.phoneNumber.null");
        }
    }
}
