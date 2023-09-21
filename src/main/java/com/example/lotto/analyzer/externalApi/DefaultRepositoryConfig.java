package com.example.lotto.analyzer.externalApi;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.lotto.analyzer.externalApi.Repository"
)
public class DefaultRepositoryConfig {
}
