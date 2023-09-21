package com.example.lotto.analyzer.externalApi;

import com.example.lotto.analyzer.externalApi.Entity.*;
import com.example.lotto.analyzer.externalApi.Repository.DrawResultGameRepository;
import com.example.lotto.analyzer.externalApi.Repository.EuroJackpotDrawResultRepository;
import com.example.lotto.analyzer.externalApi.Repository.LottoDrawResultRepository;
import com.example.lotto.analyzer.externalApi.Repository.LottoPlusDrawResultRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DataService {
    private final RestTemplate restTemplate;
    private final DrawResultGameRepository drawResultGameRepository;
    private final LottoPlusDrawResultRepository lottoPlusDrawResultGameRepository;
    private final EuroJackpotDrawResultRepository euroJackpotDrawResultRepository;

    private final String url = "https://www.lotto.pl/api/lotteries/draw-results/by-gametype?";

    public void fetchAllGamesByType(GameType gameType) {
        DrawData drawData = restTemplate.getForObject(url + "game=" + gameType + "&index=1&size=10000000&sort=drawDate&order=DESC", DrawData.class);

        if (drawData == null || drawData.getItems() == null) {
            log.warn("SOMETHING WENT WRONG WITH FETCHING DATA FOR: " + gameType);
            return;
        }

        List<DrawResultGame> resultsList = getAllResults(drawData, gameType);
        log.info("FETCHED " + resultsList.size() + " RECORDS FOR " + gameType);
        drawResultGameRepository.saveAll(resultsList);
    }

    public void fetchMissingGamesByType(GameType gameType) {
        long size = calculateMissingDrawsCount(gameType);

        if (size == 0) {
            log.warn("CALCULATED MISSING DRAWS COUNT IS EQUAL TO 0, MEANS SOMETHING WENT WRONG WHEN CALCULATING OR THERE'S PROBLEM WITH PROVIDED DAYS CONFIGURATION FOR GAME TYPE: " + gameType);
        }

        DrawData drawData = restTemplate.getForObject(url + "game=" + gameType + "&index=1&size=" + size + "&sort=drawDate&order=DESC", DrawData.class);

        if (drawData == null || drawData.getItems() == null) {
            log.warn("SOMETHING WENT WRONG WITH FETCHING DATA FOR: " + gameType);
            return;
        }

        List<DrawResultGame> result = getAllResults(drawData, gameType);

        if (!result.isEmpty()) {
            drawResultGameRepository.saveAll(result);
            log.info("FETCHED " + result.size() + " RECORDS FOR " + gameType);
        }
    }

    public List<? extends DrawResultGame> getAllResults(GameType gameType) {
        return getRepositoryBasedOnGameType(gameType).findAll(Sort.by(Sort.Direction.DESC, "drawDate"));
    }

//    public List<? extends DrawResultGame> getLatestAmountOfResults(GameType gameType, Long numberOfResults) {
//        return getRepositoryBasedOnGameType(gameType).
//    }

    /* ToDo
        Some GameType includes other, like Lotto + LottoPlus, EkstraPensja + EkstraPremia
        it's possible to reduce and merge types that comes together and fetch it only once
    */

    private List<DrawResultGame> getAllResults(DrawData drawData, GameType gameType) {
        List<DrawResultGame> games = drawData.getItems().stream().flatMap(
                item -> item.getResults()
                        .stream()
                        .filter(result -> gameType.equals(result.getGameType()))
        ).collect((Collectors.toList()));
        Collections.reverse(games);
        return games;
    }

    private long calculateMissingDrawsCount(GameType gameType) {
        OffsetDateTime sinceDate;

        sinceDate = getRepositoryBasedOnGameType(gameType).findLatestDrawDate().orElse(null);

        if (sinceDate == null) {
            return 10000000L;
        }

        long daysBetween = ChronoUnit.DAYS.between(sinceDate, OffsetDateTime.now());
        long missedDaysCount = 0;
        for (long i = 1; i <= daysBetween; i++) {
            OffsetDateTime currentDate = sinceDate.plusDays(i);
            if (getDaysConfigurationForGameType(gameType).contains(currentDate.getDayOfWeek())) {
                missedDaysCount++;
            }
        }

        return missedDaysCount;
    }

    private List<DayOfWeek> getDaysConfigurationForGameType(GameType gameType) {
        // ToDo extend this list by missing games
        return switch (gameType) {
            case Lotto, LottoPlus -> List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY);
            case EuroJackpot -> List.of(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY);
            default -> List.of();
        };
    }

    private DrawResultGameRepository<?> getRepositoryBasedOnGameType(GameType gameType) {
        return switch (gameType) {
            // case Lotto -> lottoDrawResultGameRepository;
            case LottoPlus -> lottoPlusDrawResultGameRepository;
            case EuroJackpot -> euroJackpotDrawResultRepository;
            default -> drawResultGameRepository;
        };
    }
}
