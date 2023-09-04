package com.example.lotto.analyzer.externalApi.Entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

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
@ToString
public abstract class DrawResultGame extends DrawResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Set<Integer> resultsJson;
    private Set<Integer> specialResults;
}
