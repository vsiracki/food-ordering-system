package com.food.ordering.system.elastic.query.client.repository;

import com.food.ordering.system.elastic.model.index.impl.CustomerIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CustomerElasticsearchQueryRepository  extends ElasticsearchRepository<CustomerIndexModel, String> {

    List<CustomerIndexModel> findByText(String text);
}