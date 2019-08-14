package org.manuel.mysportfolio.transformers;

import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class AnonymousTeamToTeamDtoTransformer implements Function<AnonymousTeam, TeamDto> {

    @Override
    public TeamDto apply(final AnonymousTeam anonymousTeam) {
        return anonymousTeam == null ? null : TeamDto.builder()
                .name(anonymousTeam.getName())
                .imageLink(anonymousTeam.getImageLink())
                .build();
    }

}
