package com.example.lotto.analyzer.externalApi;

//import com.example.lotto.analyzer.externalApi.Entity.Base.LottoExtendedRepository;
import com.example.lotto.analyzer.externalApi.Entity.Base.LottoExtendedRepository;
import com.example.lotto.analyzer.externalApi.Entity.DrawResultGame;
import com.example.lotto.analyzer.externalApi.Repository.DrawResultGameRepository;
import com.example.lotto.analyzer.externalApi.Repository.LottoDrawResultRepository;
//import com.example.lotto.analyzer.externalApi.Repository.LottoNonExtendedRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class DataController {
    private final DataService dataService;
    private final DrawResultGameRepository drawResultGameRepository;
    private final LottoDrawResultRepository lottoDrawResultRepository;
    private final LottoExtendedRepository lottoExtendedRepository;

    @GetMapping("/getAllResults")
    public List<? extends DrawResultGame> getData(@RequestParam GameType gameType) {
        return dataService.getAllResults(gameType);
    }

    @GetMapping("/test")
    public void test() {
        lottoDrawResultRepository.findAll();
        lottoExtendedRepository.findAll();
        lottoExtendedRepository.findLatestDrawDate();
    }
}
