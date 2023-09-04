package com.example.lotto.analyzer.externalApi.Repository;

import com.example.lotto.analyzer.externalApi.Entity.LottoDrawResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface LottoDrawResultRepository extends JpaRepository<LottoDrawResult, Long> {
    @Query("SELECT d.drawDate FROM LottoDrawResult d WHERE d.drawDate = (SELECT MAX(d2.drawDate) FROM LottoDrawResult d2)")
    LocalDateTime findLatestDrawDate();
}
