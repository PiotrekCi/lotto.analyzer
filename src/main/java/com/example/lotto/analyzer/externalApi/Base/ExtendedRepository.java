package com.example.lotto.analyzer.externalApi.Base;

import com.example.lotto.analyzer.Entity.DrawResultGame;
import com.example.lotto.analyzer.Entity.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoRepositoryBean
public interface ExtendedRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    public Optional<OffsetDateTime> findLatestDrawDate(GameType gameType);
    public List<? extends DrawResultGame> findAll(GameType gameType);
    public EnumMap<GameType, List<? extends DrawResultGame>> findAllGames();
    public List<? extends DrawResultGame> findAllContainingResult(GameType gameType, Set<Integer> desiredResult);
    public List<? extends DrawResultGame> findAllBetweenDates(GameType gameType, OffsetDateTime sinceDate, OffsetDateTime toDate);
}
