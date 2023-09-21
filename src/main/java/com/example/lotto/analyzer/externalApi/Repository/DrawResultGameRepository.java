package com.example.lotto.analyzer.externalApi.Repository;

import com.example.lotto.analyzer.externalApi.Entity.DrawResultGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DrawResultGameRepository<T extends DrawResultGame> extends JpaRepository<T, Long> {
    default Optional<OffsetDateTime> findLatestDrawDate() {
        return Optional.empty();
    }

    default List<T> findSomething() {
        new Abc().getInstanceOfT(T);
        return List.of();
    }
}
