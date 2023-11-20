package com.food.ordering.system.elastic.query.client.service;

import com.food.ordering.system.config.data.ElasticConfigData;
import com.food.ordering.system.config.data.ElasticQueryConfigData;
import com.food.ordering.system.elastic.model.index.impl.CustomerIndexModel;
import com.food.ordering.system.elastic.query.client.exception.ElasticQueryClientException;
import com.food.ordering.system.elastic.query.client.util.ElasticQueryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerElasticQueryClient implements ElasticQueryClient<CustomerIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerElasticQueryClient.class);

    private final ElasticConfigData elasticConfigData;

    private final ElasticQueryConfigData elasticQueryConfigData;

    private final ElasticsearchOperations elasticsearchOperations;

    private final ElasticQueryUtil<CustomerIndexModel> elasticQueryUtil;

    CustomerElasticQueryClient(ElasticConfigData configData,
                              ElasticQueryConfigData queryConfigData,
                              ElasticsearchOperations elasticOperations,
                              ElasticQueryUtil<CustomerIndexModel> queryUtil) {
        this.elasticConfigData = configData;
        this.elasticQueryConfigData = queryConfigData;
        this.elasticsearchOperations = elasticOperations;
        this.elasticQueryUtil = queryUtil;
    }

    @Override
    public CustomerIndexModel getIndexModelById(String id) {
        Query query = elasticQueryUtil.getSearchQueryById(id);
        SearchHit<CustomerIndexModel> searchResult = elasticsearchOperations.searchOne(query, CustomerIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        if (searchResult == null) {
            LOG.error("No document found at elasticsearch with id {}", id);
            throw new ElasticQueryClientException ("No document found at elasticsearch with id " + id);
        }
        LOG.info("Document with id {} retrieved successfully", searchResult.getId());
        return searchResult.getContent();
    }

    @Override
    public List<CustomerIndexModel> getIndexModelByText(String text) {
        Query query = elasticQueryUtil.getSearchQueryByFieldText(elasticQueryConfigData.getTextField(), text);
        return search(query, "{} of documents with text {} retrieved successfully", text);
    }

    @Override
    public List<CustomerIndexModel> getAllIndexModels() {
        Query query = elasticQueryUtil.getSearchQueryForAll();
        return search(query, "{} number of documents retrieved successfully");
    }

    private List<CustomerIndexModel> search(Query query, String logMessage, Object... logParams) {
        SearchHits<CustomerIndexModel> searchResult = elasticsearchOperations.search(query, CustomerIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        LOG.info(logMessage, searchResult.getTotalHits(), logParams);
        return searchResult.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
}

