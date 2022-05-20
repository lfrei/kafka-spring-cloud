# kafka-spring-cloud-stream
Spring Cloud Stream Showcase with Apache Kafka

## Produce Data
Add products to topic `product-in-topic`, e.g.:
```
{"id": "001", "name": "IPad", "description": "The new 2021 IPad Pro"}
{"id": "002", "name": "Nokia X20", "description": "Middleclass Nokia Phone"}
```

## Consume Data
Get products with:
```
http://localhost:8080/product/[id]
```

### Interactive Query Service

When scaling the application, the state is distributed among the state stores of the 
different instances. The interactive query service can detect the instance that is holding the
requested key and can query it over RPC Mechanisms. See documentation on
[Confluent](https://docs.confluent.io/platform/current/streams/developer-guide/interactive-queries.html#streams-developer-guide-interactive-queries-discovery)
and
[Spring Cloud Stream](https://spring.io/blog/2019/12/09/stream-processing-with-spring-cloud-stream-and-apache-kafka-streams-part-6-state-stores-and-interactive-queries)
.

To test this behaviour:

1. Create the topic `product-in-topic` with multiple partitions (e.g. 3)
2. Create multiple instances of the application by overwriting the `server.port` property (e.g. 8080, 8081, 8082)
3. Produce some data to the topic
4. Query a product from different instances. The log now states `Loading local product` or `Loading remote product`, depending on the source

## Get Topology
The Topology can be shown using the following endpoint:

```
http://localhost:8080/actuator/kafkastreamstopology/showcase
```
