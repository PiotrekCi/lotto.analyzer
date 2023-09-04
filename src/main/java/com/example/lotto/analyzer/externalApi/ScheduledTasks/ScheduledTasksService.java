package com.example.lotto.analyzer.externalApi.ScheduledTasks;

import com.example.lotto.analyzer.externalApi.DataService;
import com.example.lotto.analyzer.externalApi.GameType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasksService {
    private final DataService dataService;

    @Scheduled(cron = "0 30 22 * * TUE,THU,SAT")
    private void fetchLastLottoData() {
      dataService.fetchMissingGamesByType(GameType.Lotto);
      dataService.fetchMissingGamesByType(GameType.LottoPlus);
    }

    @Scheduled(cron = "* 0 23 * * TUE,FRI")
    private void fetchLastEuroJackpotData() {
        log.info("STARTED FETCHING " + GameType.EuroJackpot + "...");
        dataService.fetchMissingGamesByType(GameType.EuroJackpot);
        log.info("LATEST DATA FETCHED DONE");
    }

    @Scheduled(cron = "0 0 22 * * *")
    private void fetchLastEkstraPensja() {
        dataService.fetchMissingGamesByType(GameType.EkstraPensja);
        dataService.fetchMissingGamesByType(GameType.EkstraPremia);
    }

    @Scheduled(cron = "30 30 23 * * *")
    private void fetch() {
        dataService.fetchMissingGamesByType(GameType.Lotto);
        dataService.fetchMissingGamesByType(GameType.EkstraPensja);
    }
}
