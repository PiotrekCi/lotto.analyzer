package com.example.lotto.analyzer.externalApi;

import com.example.lotto.analyzer.externalApi.Entity.LottoDrawResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class DataController {
    private final DataService dataService;

    @GetMapping("/")
    public List<LottoDrawResult> getData() {
        return List.of();
    }
}
