package com.example.lotto.analyzer.externalApi.Base;

import com.example.lotto.analyzer.Entity.DrawResultGame;
import com.example.lotto.analyzer.Entity.GameType;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.*;

public class ExtendedRepositoryImpl<T extends DrawResultGame, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID> {
    private final EntityManager entityManager;

    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public Optional<OffsetDateTime> findLatestDrawDate(GameType gameType) {
        return Optional.ofNullable((OffsetDateTime) entityManager.createQuery(
                "SELECT MAX(t.drawDate) FROM "
                + DrawResultGame.getClassBasedOnGameType(gameType).getName()
                + " t"
        ).getSingleResult());
    }

    public List<? extends DrawResultGame> findAll(GameType gameType) {
        return entityManager.createQuery(
                "SELECT t FROM "
               + DrawResultGame.getClassBasedOnGameType(gameType).getName()
               + " t",
               DrawResultGame.getClassBasedOnGameType(gameType)
        ).getResultList();
    }

    public EnumMap<GameType, List<? extends DrawResultGame>> findAllGames() {
        EnumMap<GameType, List<? extends DrawResultGame>> gamesMap = new EnumMap<>(GameType.class);
        Arrays.stream(GameType.values()).forEach(
                gameType -> gamesMap.put(gameType, this.findAll(gameType))
        );
        return gamesMap;
    }

    public List<? extends DrawResultGame> findAllContainingResult(GameType gameType, Set<Integer> desiredResult) {
        return entityManager.createQuery(
                "SELECT t FROM "
                + DrawResultGame.getClassBasedOnGameType(gameType).getName()
                + " t "
                + "WHERE t.resultsJson = ?1",
                DrawResultGame.getClassBasedOnGameType(gameType)
        ).setParameter(1, desiredResult)
        .getResultList();
    }

    public List<? extends DrawResultGame> findAllBetweenDates(GameType gameType, OffsetDateTime sinceDate, OffsetDateTime toDate) {
        String queryBase = "SELECT t FROM "
                + DrawResultGame.getClassBasedOnGameType(gameType).getName()
                + " t "
                + "WHERE ";
        if (sinceDate == null && toDate == null) {

        }
        return entityManager.createQuery(
                "SELECT t FROM "
                + DrawResultGame.getClassBasedOnGameType(gameType).getName()
                + " t "
                + "WHERE ",
                DrawResultGame.getClassBasedOnGameType(gameType)
        ).getResultList();
    }
}
