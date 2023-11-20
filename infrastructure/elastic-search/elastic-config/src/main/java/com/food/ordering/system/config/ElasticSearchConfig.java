package com.food.ordering.system.config;

import com.food.ordering.system.config.data.ElasticConfigData;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.food.ordering.system.elastic")
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    private final ElasticConfigData elasticConfigData;

    public ElasticSearchConfig(ElasticConfigData configData) {
        this.elasticConfigData = configData;
    }

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticConfigData.getConnectionUrl())
                .withConnectTimeout(elasticConfigData.getConnectTimeoutMs())
                .withSocketTimeout(elasticConfigData.getSocketTimeoutMs())
                .build();
    }
}
