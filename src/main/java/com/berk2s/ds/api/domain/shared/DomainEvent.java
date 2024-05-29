package com.berk2s.ds.api.domain.shared;

import java.beans.Transient;

public interface DomainEvent {

    @Transient
    String getEventType();
}
