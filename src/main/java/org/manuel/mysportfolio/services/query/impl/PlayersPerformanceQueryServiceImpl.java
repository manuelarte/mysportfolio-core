package org.manuel.mysportfolio.services.query.impl;

import io.github.manuelarte.mysportfolio.model.documents.playersperformance.Performance;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.manuel.mysportfolio.repositories.PlayersPerformanceRepository;
import org.manuel.mysportfolio.services.query.PlayersPerformanceQueryService;
import org.springframework.stereotype.Component;

@Component
@lombok.AllArgsConstructor
class PlayersPerformanceQueryServiceImpl implements PlayersPerformanceQueryService {

  private final PlayersPerformanceRepository playersPerformanceRepository;

  @Override
  public Optional<Performance> findByMatchIdAndPlayerId(final ObjectId matchId,
      final String playerId) {
    return playersPerformanceRepository.findByMatchId(matchId)
        .flatMap(p -> p.getPerformance(playerId));
  }
}
