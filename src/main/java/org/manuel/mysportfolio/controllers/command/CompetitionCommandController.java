package org.manuel.mysportfolio.controllers.command;

import io.github.manuelarte.mysportfolio.model.dtos.CompetitionDto;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.New;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.PartialUpdate;
import io.github.manuelarte.spring.manuelartevalidation.constraints.groups.Update;
import io.jsonwebtoken.lang.Assert;
import javax.validation.groups.Default;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.services.command.CompetitionCommandService;
import org.manuel.mysportfolio.transformers.CompetitionDtoToCompetitionTransformer;
import org.manuel.mysportfolio.transformers.CompetitionToCompetitionDtoTransformer;
import org.manuel.mysportfolio.transformers.competition.CompetitionDtoToExistingCompetitionTransformer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/competitions")
@lombok.AllArgsConstructor
@lombok.extern.slf4j.Slf4j
public class CompetitionCommandController {

  private final CompetitionCommandService competitionCommandService;
  private final CompetitionDtoToCompetitionTransformer competitionDtoToCompetitionTransformer;
  private final CompetitionDtoToExistingCompetitionTransformer competitionDtoToExistingCompetitionTransformer;
  private final CompetitionToCompetitionDtoTransformer competitionToCompetitionDtoTransformer;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompetitionDto> saveCompetition(@Validated({Default.class,
      New.class}) @RequestBody final CompetitionDto competitionDto) {
    final var saved = competitionCommandService
        .save(competitionDtoToCompetitionTransformer.apply(competitionDto));
    final var location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(saved.getId()).toUri();
    log.info("Competition with id {}, created by {} saved", saved.getId(), saved.getCreatedBy());
    return ResponseEntity.created(location)
        .body(competitionToCompetitionDtoTransformer.apply(saved));

  }

  @PutMapping(value = "/{competitionId}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompetitionDto> updateCompetition(
      @PathVariable final ObjectId competitionId, @Validated({Default.class,
      Update.class}) @RequestBody final CompetitionDto competitionDto) {
    Assert.isTrue(competitionId.equals(competitionDto.getId()), "Ids don't match");
    final var updated = competitionCommandService
        .update(competitionDtoToExistingCompetitionTransformer.apply(competitionId, competitionDto));
    return ResponseEntity.ok(competitionToCompetitionDtoTransformer.apply(updated));

  }

  @PatchMapping(value = "/{competitionId}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CompetitionDto> partialUpdateCompetition(
      @PathVariable final ObjectId competitionId,
      @Validated({Default.class,
          PartialUpdate.class}) @RequestBody final CompetitionDto competitionDto) {
    return ResponseEntity.ok(competitionToCompetitionDtoTransformer
        .apply(competitionCommandService.partialUpdate(competitionId,
            competitionDtoToCompetitionTransformer.apply(competitionDto))));
  }

}
