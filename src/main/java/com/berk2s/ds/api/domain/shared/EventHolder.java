package com.berk2s.ds.api.domain.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Getter
@Slf4j
public abstract class EventHolder {
    private List<DomainEvent> domainEvents = new ArrayList<>();

    protected void registerEvent(final DomainEvent domainEvent) {
        log.info("{} event registered [event: {}]", domainEvent.getEventType(), domainEvent);

        domainEvents.add(domainEvent);
    }

    public List<DomainEvent> pickDomainEvents() {
        final List<DomainEvent> eventsToReturn = unmodifiableList(domainEvents);
        domainEvents = new ArrayList<>();
        return eventsToReturn;
    }
}

