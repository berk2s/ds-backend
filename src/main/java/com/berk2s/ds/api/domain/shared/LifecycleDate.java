package com.berk2s.ds.api.domain.shared;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class LifecycleDate implements ValueObject<LifecycleDate> {
    @EqualsAndHashCode.Include
    private LocalDateTime createdAt;
    @EqualsAndHashCode.Include
    private LocalDateTime lastModifiedAt;

    public static LifecycleDate create(LocalDateTime createdAt,
                                       LocalDateTime lastModifiedAt) {
        return new LifecycleDate(createdAt, lastModifiedAt);
    }

    @Override
    public boolean sameValueAs(LifecycleDate other) {
        return createdAt.equals(other.createdAt) && lastModifiedAt.equals(other.lastModifiedAt);
    }
}
