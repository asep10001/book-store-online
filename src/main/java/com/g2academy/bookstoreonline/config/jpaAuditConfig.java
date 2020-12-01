package com.g2academy.bookstoreonline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class jpaAuditConfig {
    @Bean
    public AuditorAware<String> auditorProvider(){
        return new com.g2academy.bookstoreonline.config.AuditorAwareImpl();
    }
}
