package com.example.lotto.analyzer.externalApi.Repository;

import com.example.lotto.analyzer.externalApi.Entity.EuroJackpotDrawResult;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface EuroJackpotDrawResultRepository extends DrawResultGameRepository<EuroJackpotDrawResult> {
    @Query(value = "SELECT MAX(draw_date) FROM euro_jackpot_draw_result", nativeQuery = true)
    Optional<OffsetDateTime> findLatestDrawDate();
}
