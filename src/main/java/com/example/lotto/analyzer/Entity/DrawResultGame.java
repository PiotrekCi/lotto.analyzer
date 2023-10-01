package com.example.lotto.analyzer.Entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.GenericDeclaration;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "gameType", visible = true, defaultImpl = UnsupportedDrawResult.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LottoDrawResult.class, name = "Lotto"),
        @JsonSubTypes.Type(value = LottoPlusDrawResult.class, name = "LottoPlus"),
        @JsonSubTypes.Type(value = SuperSzansaDrawResult.class, name = "SuperSzansa"),
        @JsonSubTypes.Type(value = EuroJackpotDrawResult.class, name = "EuroJackpot"),
        @JsonSubTypes.Type(value = EkstraPensjaDrawResult.class, name = "EkstraPensja")
})
public abstract class DrawResultGame extends DrawResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Set<Integer> resultsJson;
    private Set<Integer> specialResults;

    public static String getTableNameBasedOnGameType(GameType gameType) {
        Class<? extends DrawResultGame> entityClass = switch (gameType) {
            case Lotto -> LottoDrawResult.class;
            case LottoPlus -> LottoPlusDrawResult.class;
            case EuroJackpot -> EuroJackpotDrawResult.class;
            case SuperSzansa -> SuperSzansaDrawResult.class;
            case EkstraPensja -> EkstraPensjaDrawResult.class;
            default -> null;
        };

        if (entityClass == null) {
            return "";
        }

        return entityClass.getAnnotation(Table.class).name();
    }

    public static Class<? extends DrawResultGame> getClassBasedOnGameType(GameType gameType) {
        return switch (gameType) {
            case Lotto -> LottoDrawResult.class;
            case LottoPlus -> LottoPlusDrawResult.class;
            case EuroJackpot -> EuroJackpotDrawResult.class;
            case SuperSzansa -> SuperSzansaDrawResult.class;
            case EkstraPensja -> EkstraPensjaDrawResult.class;
            default -> DrawResultGame.class;
        };
    }
}
