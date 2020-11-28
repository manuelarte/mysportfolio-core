package org.manuel.mysportfolio.model.dtos.match;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.manuelarte.mysportfolio.model.documents.match.type.CompetitionMatchType;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;

@JsonDeserialize(builder = CompetitionMatchTypeDto.CompetitionMatchTypeDtoBuilder.class)
@Immutable
@lombok.Data
@lombok.AllArgsConstructor
@lombok.Builder(toBuilder = true)
public class CompetitionMatchTypeDto implements MatchTypeDto {

  @NotNull
  private final ObjectId competitionId;

  public static CompetitionMatchTypeDto from(final CompetitionMatchType matchType) {
    return new CompetitionMatchTypeDto(matchType.getCompetitionId());
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class CompetitionMatchTypeDtoBuilder {

  }
}
