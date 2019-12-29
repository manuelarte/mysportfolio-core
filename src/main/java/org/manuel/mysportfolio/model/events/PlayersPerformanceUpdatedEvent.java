package org.manuel.mysportfolio.model.events;

import org.manuel.mysportfolio.model.entities.match.PlayersPerformance;
import org.springframework.context.ApplicationEvent;

public class PlayersPerformanceUpdatedEvent extends ApplicationEvent {

    /**
     * Create a new PlayersPerformanceUpdatedEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public PlayersPerformanceUpdatedEvent(final PlayersPerformance source) {
        super(source);
    }
}
