package com.example.lotto.analyzer.Entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DrawResultItem extends DrawResult {
    private int multiplierValue;
    private boolean showSpecialResults;
    private boolean isNewEuroJackpotDraw;
    private List<DrawResultGame> results;
}
