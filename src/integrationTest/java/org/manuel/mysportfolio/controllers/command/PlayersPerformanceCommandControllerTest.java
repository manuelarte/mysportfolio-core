package org.manuel.mysportfolio.controllers.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.manuel.mysportfolio.ITConfiguration;
import org.manuel.mysportfolio.TestUtils;
import org.manuel.mysportfolio.model.dtos.match.PerformanceDto;
import org.manuel.mysportfolio.repositories.MatchRepository;
import org.manuel.mysportfolio.repositories.PlayersPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(ITConfiguration.class)
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlayersPerformanceCommandControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayersPerformanceRepository playersPerformanceRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void tearDown() {
        matchRepository.deleteAll();
        playersPerformanceRepository.deleteAll();
    }

    @Test
    public void testSavePerformance() throws Exception {
        final var createdBy = "123456789";
        final var match = matchRepository.save(TestUtils.createMockMatch(TestUtils.createMockAnonymousTeam(),
                TestUtils.createMockAnonymousTeam(), createdBy));
        final var performanceDto = new PerformanceDto(new BigDecimal("8"), null);

        mvc.perform(patch("/api/v1/matches/{matchId}/performances", match.getId()).contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(performanceDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value("8"))
                .andExpect(jsonPath("$.notes").doesNotExist());
    }

}