package com.redbeard.stream;

import com.redbeard.stream.domain.Product;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ProductConfig {

    public static final String STORE_NAME = "product-store";

    @Bean
    public SimpleMeterRegistry registry() {
        //required to prevent listener error, fixed in binder version 3.1.3
        return new SimpleMeterRegistry();
    }

    @Bean
    public Consumer<KStream<String, Product>> process() {
        return input -> input
                .map((key, value) -> new KeyValue<>(value.getId(), value.getName()))
                .toTable(Materialized.<String, String, KeyValueStore<Bytes, byte[]>>as(STORE_NAME)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.String()));
    }
}
