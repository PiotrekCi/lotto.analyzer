package com.example.lotto.analyzer.domainApi.service;

import com.example.lotto.analyzer.Entity.DrawResultGame;
import com.example.lotto.analyzer.Entity.GameType;
import com.example.lotto.analyzer.externalApi.Base.DrawResultGameExtendedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DrawDataService {
    private final DrawResultGameExtendedRepository drawResultGameExtendedRepository;

    public List<? extends DrawResultGame> getAllResultsForSingleGameType(GameType gameType) {
        return drawResultGameExtendedRepository.findAll(gameType);
    }

    public OffsetDateTime getLatestDrawDate(GameType gameType) {
        return drawResultGameExtendedRepository.findLatestDrawDate(gameType).orElse(null);
    }

    public EnumMap<GameType, List<? extends DrawResultGame>> getAllResultsForEveryGame() {
        return drawResultGameExtendedRepository.findAllGames();
    }

    public List<? extends DrawResultGame> findAllContainingProvidedResult(GameType gameType, Set<Integer> result) {
        return drawResultGameExtendedRepository.findAllContainingResult(gameType, result);
    }

    public <T extends DrawResultGame> Map<Set<Integer>, List<T>> findAllDuplicates(GameType gameType) {
        Map<Set<Integer>, List<T>> resultsMap = new LinkedHashMap<>();

        drawResultGameExtendedRepository.findAll(gameType)
                .forEach(drawResult -> {
                    if (resultsMap.containsKey(drawResult.getResultsJson())) {
                        List<T> x = resultsMap.get(drawResult.getResultsJson());
                        x.add((T) drawResult);
                    } else {
                        LinkedList<T> list = new LinkedList<>();
                        list.add((T) drawResult);
                        resultsMap.put(drawResult.getResultsJson(), list);
                    }
                });

        Iterator<Map.Entry<Set<Integer>, List<T>>> iterator = resultsMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Set<Integer>, List<T>> entry = iterator.next();
            Set<Integer> k = entry.getKey();
            List<T> v = entry.getValue();
            if (v.size() <= 1) {
                iterator.remove();
            }
        }

        return resultsMap;
    }

    public List<? extends DrawResultGame> findAllBetweenDates(
            GameType gameType, OffsetDateTime sinceDate, OffsetDateTime toDate
    ) {
        if (sinceDate == null && toDate == null) {
            throw new IllegalArgumentException("Either sinceDate or toDate must be provided.");
        }

        if (sinceDate != null && toDate != null && sinceDate.isAfter(toDate)) {
            throw new IllegalArgumentException("sinceDate must be before toDate.");
        }

        return drawResultGameExtendedRepository.findAllBetweenDates(gameType, sinceDate, toDate);
    }
}
