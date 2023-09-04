package com.example.lotto.analyzer.externalApi.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "unsupported_draw_result")
public class UnsupportedDrawResult extends DrawResultGame {
}
