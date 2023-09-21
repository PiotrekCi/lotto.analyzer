package com.example.lotto.analyzer.externalApi.Repository;

import com.example.lotto.analyzer.externalApi.Entity.LottoPlusDrawResult;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface EkstraPensjaDrawResultRepository extends DrawResultGameRepository<LottoPlusDrawResult> {
    @Query(value = "SELECT MAX(draw_date) FROM ekstra_pensja_draw_result", nativeQuery = true)
    Optional<OffsetDateTime> findLatestDrawDate();
}
