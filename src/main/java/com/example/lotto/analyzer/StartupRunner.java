package com.example.lotto.analyzer;

import com.example.lotto.analyzer.externalApi.FetchDataService;
import com.example.lotto.analyzer.Entity.GameType;
import com.example.lotto.analyzer.Entity.UnsupportedGameType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {
    @Value("${fetch.api.data}")
    private boolean fetchApiData;
    private final FetchDataService dataService;


    @Override
    public void run(String... args) throws Exception {
        if (fetchApiData) {
            Arrays.stream(GameType.values())
                    .filter(gameType -> Arrays.stream(UnsupportedGameType.values())
                    .noneMatch(unsGT -> (unsGT.name()).equals(gameType.name())))
                    .forEach(dataService::fetchAllGamesByType);
        }
    }
}
