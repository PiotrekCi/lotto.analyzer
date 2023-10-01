package com.example.lotto.analyzer.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class DrawResult {
    private OffsetDateTime drawDate;
    private Long drawSystemId;
    @Enumerated(EnumType.STRING)
    private GameType gameType;
}
