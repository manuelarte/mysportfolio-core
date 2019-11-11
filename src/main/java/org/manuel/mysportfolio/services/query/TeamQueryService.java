package org.manuel.mysportfolio.services.query;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.team.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TeamQueryService {

    //@PostAuthorize("hasRole('ROLE_ADMIN') or returnObject.content[]?.createdBy == authentication.principal.attributes['sub']")
    Page<Team> findAllCreatedBy(Pageable pageable, String createdBy);

    @PostAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    Optional<Team> findOne(ObjectId id);

    @PreAuthorize("hasRole('ROLE_ADMIN') or #createdBy == authentication.principal.attributes['sub']")
    int countAllByCreatedBy(String createdBy);

}
