package com.food.ordering.system.elastic.index.client.service;

import com.food.ordering.system.elastic.model.IndexModel;

import java.util.List;

public interface ElasticIndexClient <T extends IndexModel> {
    List<String> save(List<T> documents);
}
