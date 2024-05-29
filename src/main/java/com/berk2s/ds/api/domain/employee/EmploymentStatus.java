package com.berk2s.ds.api.domain.employee;

import com.berk2s.ds.api.domain.shared.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class EmploymentStatus implements ValueObject<EmploymentStatus> {
    @EqualsAndHashCode.Include
    private LocalDateTime startedAt;
    @EqualsAndHashCode.Include
    private LocalDateTime exitAt;

    private EmploymentStatus() {
        hired();
    }

    public EmploymentStatus(LocalDateTime startedAt,
                            LocalDateTime exitAt) {
        this.startedAt = startedAt;
        this.exitAt = exitAt;
    }

    private void hired() {
        startedAt = LocalDateTime.now();
    }

    public void fired() {
        exitAt = LocalDateTime.now();
    }

    public static EmploymentStatus create() {
        return new EmploymentStatus();
    }

    public static EmploymentStatus create(LocalDateTime startedAt,
                                          LocalDateTime exitAt) {
        return new EmploymentStatus(startedAt, exitAt);
    }

    @Override
    public boolean sameValueAs(EmploymentStatus other) {
        return startedAt.equals(other.getStartedAt());
    }
}
