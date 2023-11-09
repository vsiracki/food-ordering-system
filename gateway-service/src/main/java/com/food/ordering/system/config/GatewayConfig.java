package com.food.ordering.system.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class GatewayConfig {
//
//
//    @Bean
//    Customizer<ReactiveResilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
//        return reactiveResilience4JCircuitBreakerFactory ->
//                reactiveResilience4JCircuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                        .timeLimiterConfig(TimeLimiterConfig.custom()
//                                .timeoutDuration(Duration.ofMillis(3000))
//                                .build())
//                        .circuitBreakerConfig(CircuitBreakerConfig.custom()
//                                .failureRateThreshold(50)
//                                .slowCallRateThreshold(50)
//                                .slowCallDurationThreshold(Duration.ofMillis(50))
//                                .permittedNumberOfCallsInHalfOpenState(10)
//                                .slidingWindowSize(10)
//                                .minimumNumberOfCalls(10)
//                                .waitDurationInOpenState(Duration.ofMillis(60000))
//                                .build())
//                        .build());
//    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder (id)
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .build());
    }

}
