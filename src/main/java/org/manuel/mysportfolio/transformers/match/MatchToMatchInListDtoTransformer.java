package org.manuel.mysportfolio.transformers.match;

import org.manuel.mysportfolio.model.dtos.match.MatchInListDto;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.entities.TeamOption;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.model.entities.match.events.GoalMatchEvent;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@lombok.AllArgsConstructor
public class MatchToMatchInListDtoTransformer implements Function<Match, MatchInListDto> {

    private final TeamTypeToTeamInMatchListDtoTransformer teamTypeToTeamInMatchListDtoTransformer;

    @Override
    public MatchInListDto apply(final Match match) {
        return match == null ? null : MatchInListDto.builder()
                .id(match.getId().toString())
                .competitionId(Optional.ofNullable(match.getCompetitionId()).map(c -> c.toString()).orElse(null))
                .version(match.getVersion())
                .sport(match.getSport())
                .type(match.getType())
                .startDate(match.getStartDate())
                .homeTeam(getTeamName(match.getHomeTeam()))
                .awayTeam(getTeamName(match.getAwayTeam()))
                .playedFor(match.getPlayedFor())
                .homeGoals(getGoals(match.getEvents(), TeamOption.HOME_TEAM))
                .awayGoals(getGoals(match.getEvents(), TeamOption.AWAY_TEAM))
                .chips(match.getChips())
                .build();
    }

    private <T extends TeamType> String getTeamName(T team) {
        return Optional.ofNullable(teamTypeToTeamInMatchListDtoTransformer.apply(team)).map(TeamDto::getName).orElse(null);
    }

    private int getGoals(final List<MatchEvent> events, final TeamOption teamOption) {
        return (int) events.stream().filter(e -> e instanceof GoalMatchEvent).filter(gE -> teamOption.equals(((GoalMatchEvent) gE).getTeam())).count();
    }

}
