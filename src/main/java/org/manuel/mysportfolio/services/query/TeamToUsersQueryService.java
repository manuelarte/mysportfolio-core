package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.teamtouser.TeamToUsers;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface TeamToUsersQueryService {

    // only admins or people that one time where in the team can get that info
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    Optional<TeamToUsers> findByTeamId(ObjectId teamId);

    // admin or the same user can perform this call
    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == authentication.principal.attributes['sub']")
    List<TeamToUsers> findByUsersExists(String userId);


}
