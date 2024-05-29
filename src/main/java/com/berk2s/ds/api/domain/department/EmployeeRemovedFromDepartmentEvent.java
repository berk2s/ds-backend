package com.berk2s.ds.api.domain.department;

import com.berk2s.ds.api.domain.shared.DomainEvent;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeRemovedFromDepartmentEvent implements DomainEvent {
    private final String eventType = "EMPLOYEE_REMOVED_FROM_DEPARTMENT_EVENT";

    private Long departmentId;
    private String employeeId;

    @Override
    public String getEventType() {
        return eventType;
    }
}
