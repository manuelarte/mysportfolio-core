package org.manuel.mysportfolio.transformers.match;

import io.github.manuelarte.mysportfolio.model.documents.match.AnonymousTeam;
import io.github.manuelarte.mysportfolio.model.dtos.team.AnonymousTeamDto;
import java.util.function.Function;
import org.manuel.mysportfolio.transformers.team.TeamImageDtoToTeamImageTransformer;
import org.manuel.mysportfolio.transformers.team.TeamKitDtoToTeamKitTransformer;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
public class AnonymousTeamDtoToAnonymousTeamTransformer implements
    Function<AnonymousTeamDto, AnonymousTeam> {

  private final TeamImageDtoToTeamImageTransformer teamImageDtoToTeamImageTransformer;
  private final TeamKitDtoToTeamKitTransformer teamKitDtoToTeamKitTransformer;

  @Override
  public AnonymousTeam apply(final AnonymousTeamDto anonymousTeamDto) {
    return anonymousTeamDto == null ? null : create(anonymousTeamDto);
  }

  private AnonymousTeam create(final AnonymousTeamDto anonymousTeamDto) {
    final var anonymousTeam = new AnonymousTeam();
    anonymousTeam.setName(anonymousTeamDto.getName());
    anonymousTeam.setTeamKit(teamKitDtoToTeamKitTransformer.apply(anonymousTeamDto.getTeamKit()));
    anonymousTeam.setTeamImage(teamImageDtoToTeamImageTransformer.apply(anonymousTeamDto.getTeamImage()));
    return anonymousTeam;
  }

}
