package com.example.lotto.analyzer.externalApi.Entity.Base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.time.OffsetDateTime;

@NoRepositoryBean
public interface ExtendedRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    public OffsetDateTime findLatestDrawDate();
}
