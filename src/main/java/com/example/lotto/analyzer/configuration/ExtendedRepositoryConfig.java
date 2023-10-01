package com.example.lotto.analyzer.configuration;

import com.example.lotto.analyzer.externalApi.Base.ExtendedRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.lotto.analyzer.externalApi.Base",
        repositoryBaseClass = ExtendedRepositoryImpl.class
)
public class ExtendedRepositoryConfig {
}
