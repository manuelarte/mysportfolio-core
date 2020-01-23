package org.manuel.mysportfolio.listeners;

import javax.annotation.Nonnull;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.events.SportEvent;
import org.manuel.mysportfolio.services.command.UserBadgesCommandService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class AddBadgesListener implements ApplicationListener<SportEvent<?>> {

	private final UserBadgesCommandService userBadgesCommandService;
	private final UserIdProvider userIdProvider;

	@Override
	public void onApplicationEvent(@Nonnull final SportEvent<?> event) {
		final var userId = userIdProvider.getUserId();
		userBadgesCommandService.addBadges(userId, event);
	}

}