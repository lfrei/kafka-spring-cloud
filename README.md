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
