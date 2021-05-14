package com.redbeard.stream;

import com.redbeard.stream.domain.Product;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.redbeard.stream.ProductConfig.STORE_NAME;

@RestController
public class ProductController {

    @Autowired
    private InteractiveQueryService queryService;

    private ReadOnlyKeyValueStore<String, Product> keyValueStore;

    @GetMapping("/product/{id}")
    Product getProduct(@PathVariable String id) {
        if (keyValueStore == null) {
            keyValueStore = queryService.getQueryableStore(STORE_NAME, QueryableStoreTypes.keyValueStore());
        }

        return keyValueStore.get(id);
    }
}
