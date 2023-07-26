package com.example.reactive.functional;

import org.junit.jupiter.api.Order;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class TestConfig {
    @Bean
    @ConditionalOnMissingBean
    public WebProperties.Resources webPropResources() {
        return new WebProperties.Resources();
    }
}
