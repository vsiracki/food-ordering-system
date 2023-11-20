package com.food.ordering.system.elastic.model.index.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.ordering.system.elastic.model.IndexModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;

@Data
@Builder
@Document(indexName = "#{@elasticConfigData.indexName}")
public class CustomerIndexModel implements IndexModel {
    @JsonProperty
    private String id;
    @JsonProperty
    private String userId;
    @JsonProperty
    private String username;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    @JsonProperty
    private ZonedDateTime createdAt;
}