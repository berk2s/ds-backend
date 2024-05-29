package com.berk2s.ds.api.infrastructure.common;

import com.berk2s.ds.api.application.shared.DomainEventHandler;
import com.berk2s.ds.api.domain.shared.DomainEvent;
import com.berk2s.ds.api.domain.shared.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class DomainEventPublisher implements EventPublisher {

    private final ApplicationContext context;

    @Override
    public void publish(List<DomainEvent> events) {
        String[] handlerBeanNames = context.getBeanNamesForType(DomainEventHandler.class);
        for (String beanName : handlerBeanNames) {
            DomainEventHandler handler = (DomainEventHandler) context.getBean(beanName);

            events
                    .forEach((event) -> {
                        if (!handler.canHandle(event)) {
                            log.info("{} can't handle the given event {}", handler.getClass(), event.getClass());
                            return;
                        }

                        try {
                            handler.handle(event);
                            log.info("Event has been forwarded... [event: {}]", event);
                        } catch (Exception e) {
                            log.error("Failed to handle event with handler " + handler.getClass().getSimpleName(), e);
                        }
                    });
        }

    }
}