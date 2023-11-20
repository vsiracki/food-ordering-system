package com.food.ordering.system.elastic.query.client.service;

import com.food.ordering.system.elastic.model.IndexModel;

import java.util.List;

public interface ElasticQueryClient <T extends IndexModel> {

    T getIndexModelById(String id);

    List<T> getIndexModelByText(String text);

    List<T> getAllIndexModels();
}
