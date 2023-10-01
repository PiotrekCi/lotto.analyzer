package com.example.lotto.analyzer.externalApi.ScheduledTasks;

import com.example.lotto.analyzer.externalApi.FetchDataService;
import com.example.lotto.analyzer.Entity.GameType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasksService {
    private final FetchDataService dataService;

    @Scheduled(cron = "0 30 22 * * TUE,THU,SAT")
    private void fetchLastLottoData() {
      log.info("STARTED FETCHING " + GameType.Lotto + "...");
      dataService.fetchMissingGamesByType(GameType.Lotto);
      log.info("STARTED FETCHING " + GameType.LottoPlus + "...");
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
        log.info("STARTED FETCHING " + GameType.EkstraPensja + "...");
        dataService.fetchMissingGamesByType(GameType.EkstraPensja);
        log.info("STARTED FETCHING " + GameType.EkstraPremia + "...");
        dataService.fetchMissingGamesByType(GameType.EkstraPremia);
    }
}
