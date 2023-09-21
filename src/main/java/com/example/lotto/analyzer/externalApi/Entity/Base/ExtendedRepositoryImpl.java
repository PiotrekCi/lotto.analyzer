package com.example.lotto.analyzer.externalApi.Entity.Base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class ExtendedRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID> {
    private final EntityManager entityManager;
    private final Class<T> type;

    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.type = entityInformation.getJavaType();
    }

    @Override
    public OffsetDateTime findLatestDrawDate() {
        System.out.println(type.getAnnotation(Table.class).name());
        return OffsetDateTime.now();
    }
}
