package com.example.lotto.analyzer.externalApi.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "lotto_draw_result")
public class LottoDrawResult extends DrawResultGame {
}
