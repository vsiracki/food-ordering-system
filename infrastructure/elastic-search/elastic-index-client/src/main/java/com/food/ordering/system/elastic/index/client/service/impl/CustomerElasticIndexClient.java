package com.food.ordering.system.elastic.index.client.service.impl;

import com.food.ordering.system.config.data.ElasticConfigData;
import com.food.ordering.system.elastic.index.client.service.ElasticIndexClient;
import com.food.ordering.system.elastic.index.client.util.ElasticIndexUtil;
import com.food.ordering.system.elastic.model.index.impl.CustomerIndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerElasticIndexClient implements ElasticIndexClient<CustomerIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerElasticIndexClient.class);

    private final ElasticConfigData elasticConfigData;

    private final ElasticsearchOperations elasticsearchOperations;

    private final ElasticIndexUtil<CustomerIndexModel> elasticIndexUtil;

    public CustomerElasticIndexClient(ElasticConfigData elasticConfigData,
                                      ElasticsearchOperations elasticsearchOperations,
                                      ElasticIndexUtil<CustomerIndexModel> elasticIndexUtil) {
        this.elasticConfigData = elasticConfigData;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticIndexUtil = elasticIndexUtil;
    }

    @Override
    public List<String> save(List<CustomerIndexModel> documents) {
        List<IndexQuery> indexQueries = elasticIndexUtil.getIndexQueries(documents);
        List<String> documentIds = elasticsearchOperations.bulkIndex(
                indexQueries,
                IndexCoordinates.of(elasticConfigData.getIndexName())
        ).stream().map(IndexedObjectInformation::id).collect(Collectors.toList());
        LOG.info("Documents indexed successfully with type: {} and ids: {}", CustomerIndexModel.class.getName(),
                documentIds);
        return documentIds;
    }
}
