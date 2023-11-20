package com.food.ordering.system.elastic.index.client.repository;

import com.food.ordering.system.elastic.model.index.impl.CustomerIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerElasticsearchIndexRepository extends ElasticsearchRepository<CustomerIndexModel, String> {

}