package com.example.lotto.analyzer.externalApi;

import com.example.lotto.analyzer.externalApi.Entity.Base.ExtendedRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.lotto.analyzer.externalApi.Entity.Base",
        repositoryBaseClass = ExtendedRepositoryImpl.class
)
public class ExtendedRepositoryConfig {
}
