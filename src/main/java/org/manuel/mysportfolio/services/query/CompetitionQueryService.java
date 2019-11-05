package org.manuel.mysportfolio.services.query;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.entities.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface CompetitionQueryService {

    //@PostAuthorize("hasRole('ROLE_ADMIN') or returnObject.orElse(null)?.createdBy == authentication.principal.attributes['sub']")
    Page<Competition> findAllCreatedBy(Pageable pageable, String createdBy);

    @PostAuthorize("hasRole('ROLE_ADMIN') or returnObject.orElse(null)?.createdBy == authentication.principal.attributes['sub']")
    Optional<Competition> findOne(ObjectId id);

    @PreAuthorize("hasRole('ROLE_ADMIN') or #createdBy == authentication.principal.attributes['sub']")
    int countAllByCreatedBy(String createdBy);

}
