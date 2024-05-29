package com.berk2s.ds.api.domain.shared;

import java.util.List;

public interface EventPublisher {

    void publish(List<DomainEvent> event);
}
