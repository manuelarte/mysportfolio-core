package org.manuel.mysportfolio.controllers.command.user;

import io.jsonwebtoken.lang.Assert;
import org.manuel.mysportfolio.config.UserIdProvider;
import org.manuel.mysportfolio.model.dtos.team.TeamDto;
import org.manuel.mysportfolio.model.dtos.user.UserTeamDto;
import org.manuel.mysportfolio.model.entities.teamtouser.UserInTeam;
import org.manuel.mysportfolio.services.command.TeamCommandService;
import org.manuel.mysportfolio.services.command.TeamToUsersCommandService;
import org.manuel.mysportfolio.services.query.TeamToUsersQueryService;
import org.manuel.mysportfolio.transformers.team.PartialTeamDtoToTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamDtoToExistingTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamDtoToTeamTransformer;
import org.manuel.mysportfolio.transformers.team.TeamToTeamDtoTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamDtoToUserInTeamTransformer;
import org.manuel.mysportfolio.transformers.teamtousers.UserInTeamToUserInTeamDtoTransformer;
import org.manuel.mysportfolio.validation.NewEntity;
import org.manuel.mysportfolio.validation.PartialUpdateEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.groups.Default;

@RestController
@RequestMapping("/api/v1/users/me/teams")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class UserTeamCommandController {

    private final TeamCommandService teamCommandService;
    private final TeamToUsersQueryService teamToUsersQueryService;
    private final TeamToUsersCommandService teamToUsersCommandService;
    private final TeamDtoToTeamTransformer teamDtoToTeamTransformer;
    private final TeamToTeamDtoTransformer teamToTeamDtoTransformer;
    private final UserInTeamToUserInTeamDtoTransformer userInTeamToUserInTeamDtoTransformer;
    private final UserInTeamDtoToUserInTeamTransformer userInTeamDtoToUserInTeamTransformer;
    private final UserIdProvider userIdProvider;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserTeamDto> saveUserTeam(@Validated({Default.class, NewEntity.class}) @RequestBody final UserTeamDto userTeamDto) {
        final var teamSaved = teamCommandService.save(teamDtoToTeamTransformer.apply(userTeamDto.getTeam()));
        final UserInTeam userInTeamSaved;
        // this should be part of an hypothetical service
        if (userTeamDto.getUserInTeam() != null) {
            userInTeamSaved = teamToUsersCommandService.updateUserInTeam(teamSaved.getId(), getUserId(), userInTeamDtoToUserInTeamTransformer.apply(userTeamDto.getUserInTeam()));
        } else {
            userInTeamSaved = teamToUsersQueryService.findByTeamId(teamSaved.getId()).get().getUsers().get(getUserId());
        }
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(teamSaved.getId()).toUri();
        log.info("Team with id {}, created by {}, saved", teamSaved.getId(), teamSaved.getCreatedBy());
        return ResponseEntity.created(location).body(new UserTeamDto(teamToTeamDtoTransformer.apply(teamSaved),
                userInTeamToUserInTeamDtoTransformer.apply(userInTeamSaved)));
    }

    private String getUserId() {
        return userIdProvider.getUserId();
    }


}
