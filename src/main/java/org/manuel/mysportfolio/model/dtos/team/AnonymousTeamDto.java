package org.manuel.mysportfolio.model.dtos.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.net.URL;

/**
 * Dto to be used if the home/away team is an anonymous team when user inputs a match
 */
@JsonDeserialize(builder = AnonymousTeamDto.AnonymousTeamDtoBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("anonnymous")
@lombok.AllArgsConstructor
@lombok.Value
@lombok.Builder(toBuilder = true)
public class AnonymousTeamDto implements TeamInMatchDto {

    @NotEmpty
    @Max(30)
    private final String name;

    @org.hibernate.validator.constraints.URL
    private final URL imageLink;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class AnonymousTeamDtoBuilder {

    }
}
