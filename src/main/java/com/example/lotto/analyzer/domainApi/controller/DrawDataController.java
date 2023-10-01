package com.example.lotto.analyzer.domainApi.controller;

import com.example.lotto.analyzer.domainApi.service.DrawDataService;
import com.example.lotto.analyzer.Entity.DrawResultGame;
import com.example.lotto.analyzer.Entity.GameType;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
public class DrawDataController {
    private final DrawDataService drawDataService;

    @GetMapping("/gamesResults")
    public EnumMap<GameType, List<? extends DrawResultGame>> getAllResultsForEveryGame() {
        return drawDataService.getAllResultsForEveryGame();
    }

    @GetMapping("/singleGameResults")
    public List<? extends DrawResultGame> getAllGameResults(@RequestParam GameType gameType) {
        return drawDataService.getAllResultsForSingleGameType(gameType);
    }

    @GetMapping("/latestDrawDate")
    public OffsetDateTime getLatestDrawDate(@RequestParam GameType gameType) {
        return drawDataService.getLatestDrawDate(gameType);
    }

    @GetMapping("/providedResultMatchings")
    public List<? extends DrawResultGame> getExpectedResultDraws(@RequestParam GameType gameType, @RequestParam Set<Integer> result) {
        return drawDataService.findAllContainingProvidedResult(gameType, result);
    }

    @GetMapping("/allResultDuplicates")
    public Map<Set<Integer>, List<DrawResultGame>> getAllGamesWithResultDuplication(@RequestParam GameType gameType) {
        return drawDataService.findAllDuplicates(gameType);
    }

    @GetMapping("/specifiedPeriodGameResults")
    public List<? extends DrawResultGame> getDrawsBetweenDates(
            @RequestParam GameType gameType,
            @RequestParam(required = false) @NonNull OffsetDateTime sinceDate,
            @RequestParam(required = false) @NonNull OffsetDateTime toDate
    ) {
        return drawDataService.findAllBetweenDates(gameType, sinceDate, toDate);
    }
}
