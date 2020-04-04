package org.manuel.mysportfolio.transformers.team;

import java.util.Optional;
import java.util.function.BiFunction;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.exceptions.EntityNotFoundException;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@lombok.AllArgsConstructor
public class PartialTeamDtoToTeamTransformer implements BiFunction<String, TeamDto, Team> {

  private final TeamQueryService teamQueryService;

  @Override
  public Team apply(@NotNull final String teamId, @NotNull final TeamDto updatedFieldsTeamDto) {
    Assert.notNull(teamId, "The team id in a partial update can't be null");
    Assert.notNull(updatedFieldsTeamDto,
        "The updated pojo for team in a partial update can't be null");
    final var mixed = teamQueryService.findOne(new ObjectId(teamId))
        .orElseThrow(() -> new EntityNotFoundException(String
            .format("Team with id %s not found and can't be patch", updatedFieldsTeamDto.getId())));
    Optional.ofNullable(updatedFieldsTeamDto.getName()).ifPresent(mixed::setName);
    Optional.ofNullable(updatedFieldsTeamDto.getTeamKit()).ifPresent(mixed::setTeamKit);
    Optional.ofNullable(updatedFieldsTeamDto.getTeamImage()).ifPresent(mixed::setTeamImage);
    return mixed;
  }
}
