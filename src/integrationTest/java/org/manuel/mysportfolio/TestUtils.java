package org.manuel.mysportfolio;

import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.dtos.match.MatchDto;
import org.manuel.mysportfolio.model.dtos.team.AnonymousTeamDto;
import org.manuel.mysportfolio.model.dtos.team.RegisteredTeamDto;
import org.manuel.mysportfolio.model.dtos.team.TeamInMatchDto;
import org.manuel.mysportfolio.model.entities.match.AnonymousTeam;
import org.manuel.mysportfolio.model.entities.match.Match;
import org.manuel.mysportfolio.model.entities.match.RegisteredTeam;
import org.manuel.mysportfolio.model.entities.match.TeamType;
import org.manuel.mysportfolio.model.entities.team.Team;

public class TestUtils {

    public static Team createMockTeam() {
        final var team = new Team();
        team.setName(RandomStringUtils.randomAlphabetic(5));
        return team;
    }

    public static AnonymousTeam createMockAnonymousTeam() {
        final var anonymousTeam = new AnonymousTeam();
        anonymousTeam.setName(RandomStringUtils.randomAlphabetic(5));
        return anonymousTeam;
    }

    public static AnonymousTeamDto createMockAnonymousTeamDto() {
        return AnonymousTeamDto.builder()
                .name(RandomStringUtils.randomAlphabetic(5))
                .build();
    }

    public static RegisteredTeam createMockRegisteredTeam() {
        final var registeredTeam = new RegisteredTeam();
        registeredTeam.setTeamId(new ObjectId());
        return registeredTeam;
    }

    public static RegisteredTeamDto createMockRegisteredTeamDto(ObjectId teamId) {
        return RegisteredTeamDto.builder()
                .teamId(teamId.toString())
                .build();
    }

    public static Match createMockMatch(final TeamType homeTeam, final TeamType awayTeam) {
        final var match = new Match();
        match.setSport(Sport.FOOTBALL);
        match.setType(SportType.ELEVEN_A_SIDE);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        return match;
    }

    public static <HT extends TeamInMatchDto, AT extends TeamInMatchDto> MatchDto<HT, AT> createMockMatchDto(final HT homeTeam, final AT awayTeam) {
        return MatchDto.builder()
                .sport(Sport.FOOTBALL)
                .type(SportType.ELEVEN_A_SIDE)
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .build();
    }
}
