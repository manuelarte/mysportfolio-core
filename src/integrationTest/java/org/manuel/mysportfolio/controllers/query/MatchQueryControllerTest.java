package org.manuel.mysportfolio.controllers.query;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.manuelarte.mysportfolio.model.documents.match.RegisteredTeam;
import javax.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.mysportfolio.ItConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.TeamRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(ItConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MatchQueryControllerTest {

  @Inject
  private TeamRepository teamRepository;

  @Inject
  private MatchRepository matchRepository;

  @Inject
  private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  @AfterEach
  public void tearDown() {
    matchRepository.deleteAll();
    teamRepository.deleteAll();
  }

  @Test
  public void testGetMatches() throws Exception {
    final var teamSaved = teamRepository.save(TestUtils.createMockTeam());
    final var registeredTeam = new RegisteredTeam();
    registeredTeam.setTeamId(teamSaved.getId());

    final String createdBy = "123456789";

    matchRepository.save(
        TestUtils.createMockMatch(registeredTeam, TestUtils.createMockAnonymousTeam(), createdBy));
    matchRepository.save(
        TestUtils.createMockMatch(TestUtils.createMockAnonymousTeam(), registeredTeam, createdBy));

    mvc.perform(get("/api/v1/matches").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content", hasSize((int) matchRepository.count())));
  }

}
