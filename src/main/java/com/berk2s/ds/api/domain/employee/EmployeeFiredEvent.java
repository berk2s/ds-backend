package com.berk2s.ds.api.domain.employee;

import com.berk2s.ds.api.domain.shared.DomainEvent;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeFiredEvent implements DomainEvent {
    private final String eventType = "INSERT_PUBLISHED_EVENT";

    private String id;

    @Override
    public String getEventType() {
        return eventType;
    }
}
