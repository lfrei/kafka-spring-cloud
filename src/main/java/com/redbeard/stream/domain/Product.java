package com.redbeard.stream.domain;

import lombok.Data;
import org.springframework.kafka.support.serializer.JsonSerde;

@Data
public class Product {
    private String id;
    private String name;
    private String description;

    public static JsonSerde<Product> serde() {
        return new JsonSerde<>(Product.class).ignoreTypeHeaders();
    }
}
