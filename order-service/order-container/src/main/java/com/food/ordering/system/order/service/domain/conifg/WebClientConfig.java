package com.food.ordering.system.order.service.domain.conifg;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.api.v1+json")
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.api.v1+json")
                .clientConnector(new ReactorClientHttpConnector (getHttpClient()))
                .codecs(clientCodecConfigurer ->
                        clientCodecConfigurer
                                .defaultCodecs()
                                .maxInMemorySize(10485760));
    }

    private HttpClient getHttpClient() {
        return HttpClient.create ( )
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler (10000,
                            TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler (10000,
                            TimeUnit.MILLISECONDS));
                });
    }
}
