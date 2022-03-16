package com.redbeard.stream;

import com.redbeard.stream.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.redbeard.stream.ProductConfig.STORE_NAME;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    private InteractiveQueryService queryService;

    private ReadOnlyKeyValueStore<String, Product> keyValueStore;

    @GetMapping("/product/{id}")
    Product getProduct(@PathVariable String id) {
        HostInfo hostInfo = queryService.getHostInfo(STORE_NAME, id, new StringSerializer());

        if (queryService.getCurrentHostInfo().equals(hostInfo)) {
            return getLocalProduct(id);
        } else {
            return getRemoteProduct(id, hostInfo);
        }
    }

    private Product getLocalProduct(String id) {
        log.info("Loading local product {}", id);

        if (keyValueStore == null) {
            keyValueStore = queryService.getQueryableStore(STORE_NAME, QueryableStoreTypes.keyValueStore());
        }
        return keyValueStore.get(id);
    }

    private Product getRemoteProduct(String id, HostInfo hostInfo) {
        log.info("Loading remote product {} from {}:{}", id, hostInfo.host(), hostInfo.port());

        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = String.format("http://%s:%d/%s/%s", hostInfo.host(), hostInfo.port(), "product", id);
        return restTemplate.getForObject(requestUrl, Product.class);
    }
}
