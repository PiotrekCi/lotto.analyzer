package com.example.lotto.analyzer.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.lotto.analyzer"
)
public class DefaultRepositoryConfig {
}
