package com.duro.edc_koko.application_config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.duro.edc_koko")
@EnableJpaRepositories("com.duro.edc_koko")
@EnableTransactionManagement
public class DomainConfig {
}
