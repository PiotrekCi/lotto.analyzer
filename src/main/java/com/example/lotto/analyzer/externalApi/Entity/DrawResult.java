package com.example.lotto.analyzer.externalApi.Entity;

import com.example.lotto.analyzer.externalApi.GameType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class DrawResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime drawDate;
    private Long drawSystemId;
    @Enumerated(EnumType.STRING)
    private GameType gameType;
}
