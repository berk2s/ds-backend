package com.berk2s.ds.api.application.shared;

import com.berk2s.ds.api.domain.shared.DomainEvent;

public interface DomainEventHandler<T extends DomainEvent> {
    void handle(T event);
    boolean canHandle(DomainEvent event);
}
