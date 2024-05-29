package com.berk2s.ds.api.application.shared;

import com.berk2s.ds.api.domain.shared.EventHolder;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UseCaseHandler<T extends EventHolder, R extends UseCase> {
    @PreAuthorize("hasRole('SUPER_USER')")
    T execute(final R useCase);
}

