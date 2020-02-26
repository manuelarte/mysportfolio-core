package org.manuel.mysportfolio.model.entities.user;

import java.time.Year;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.services.query.CompetitionQueryService;
import org.manuel.mysportfolio.services.query.TeamQueryService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.function.Predicate;

public enum AppMembership {

    FREE(2, 2, 2),
    NOOB(3, 3, 3),
    ADVANCE(4, 4, 3),
    PREMIUM(10, 10, 4);

    private final int maxNumberOfTeams;
    private final int maxNumberOfCompetitions;
    private final int maxNumberOfMatchesInOneWeek;

    AppMembership(final int maxNumberOfTeams, final int maxNumberOfCompetitions, final int maxNumberOfMatchesInOneWeek) {
        this.maxNumberOfTeams = maxNumberOfTeams;
        this.maxNumberOfCompetitions = maxNumberOfCompetitions;
        this.maxNumberOfMatchesInOneWeek = maxNumberOfMatchesInOneWeek;
    }

    public Predicate<OAuth2User> canSaveTeam(final TeamQueryService teamQueryService, final Year year) {
        return oAuth2User -> {
            final var externalUserId = oAuth2User.getAttributes().get("sub").toString();
            return teamQueryService.countAllByCreatedByInYear(externalUserId, year) < maxNumberOfTeams;
        };
    }

    public Predicate<OAuth2User> canSaveCompetition(final CompetitionQueryService competitionQueryService, final Year year) {
        return oAuth2User -> {
            final var externalUserId = oAuth2User.getAttributes().get("sub").toString();
            return competitionQueryService.countAllByCreatedByInYear(externalUserId, year) < maxNumberOfCompetitions;
        };
    }

    public Predicate<OAuth2User> canSaveMatch(final MatchRepository matchRepository, final LocalDate localDate) {
        return oAuth2User -> {
            final var externalUserId = oAuth2User.getAttributes().get("sub").toString();
            final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
            final DayOfWeek lastDayOfWeek = firstDayOfWeek.minus(1);
            final LocalDate from = localDate.with( TemporalAdjusters.previous( firstDayOfWeek ) ) ;
            final LocalDate to = localDate.with( TemporalAdjusters.nextOrSame( lastDayOfWeek ) ) ;

            return matchRepository.countAllByCreatedDateBetweenAndCreatedBy(from, to, externalUserId) < maxNumberOfMatchesInOneWeek;
        };
    }

}
