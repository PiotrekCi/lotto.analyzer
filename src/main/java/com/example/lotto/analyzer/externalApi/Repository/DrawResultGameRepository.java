package com.example.lotto.analyzer.externalApi.Repository;

import com.example.lotto.analyzer.externalApi.Entity.DrawResultGame;
import com.example.lotto.analyzer.externalApi.Entity.*;
import com.example.lotto.analyzer.externalApi.Entity.EuroJackpotDrawResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrawResultGameRepository extends JpaRepository<DrawResultGame, Long> {
    /**
        Method allows to save list of entities in correct Database schema based on type, that is cast by {@link DrawResultGame} by property gameType
        @param entities: <br/>
        {@link EkstraPensjaDrawResult},<br/>
        {@link EuroJackpotDrawResult},<br/>
        {@link LottoDrawResult},<br/>
        {@link LottoPlusDrawResult},<br/>
        {@link SuperSzansaDrawResult},<br/>
        Unsupported type will be treated as {@link UnsupportedDrawResult}
    */
    @Override
    <S extends DrawResultGame> List<S> saveAll(Iterable<S> entities);

    /**
     Method allows to save single entity in correct Database schema based on type, that is cast by {@link DrawResultGame} by property gameType
     @param entity: <br/>
     {@link EkstraPensjaDrawResult},<br/>
     {@link EuroJackpotDrawResult},<br/>
     {@link LottoDrawResult},<br/>
     {@link LottoPlusDrawResult},<br/>
     {@link SuperSzansaDrawResult},<br/>
     Unsupported type will be treated as {@link UnsupportedDrawResult}
     */
    @Override
    <S extends DrawResultGame> S save(S entity);
}
