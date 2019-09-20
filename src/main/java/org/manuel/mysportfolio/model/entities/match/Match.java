package org.manuel.mysportfolio.model.entities.match;

import org.bson.types.ObjectId;
import org.manuel.mysportfolio.model.Sport;
import org.manuel.mysportfolio.model.SportType;
import org.manuel.mysportfolio.model.entities.match.events.MatchEvent;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "matches")
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Match<HomeTeamType extends TeamType, AwayTeamType extends TeamType> {

    @Id
    private ObjectId id;

    private Sport sport;

    private SportType type;

    private HomeTeamType homeTeam;

    private AwayTeamType awayTeam;

    private List<MatchEvent> events = new ArrayList<>();

    @CreatedBy
    @NotNull
    private String createdBy;

    @SuppressWarnings("unused")
    private void setCreator(final String createdBy) {
        this.createdBy = createdBy;
    }

}
