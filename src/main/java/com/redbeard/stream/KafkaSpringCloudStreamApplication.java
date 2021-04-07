package com.redbeard.stream;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class KafkaSpringCloudStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringCloudStreamApplication.class, args);
	}

	@Bean
	public SimpleMeterRegistry registry() {
		//required to prevent listener error, fixed in binder version 3.1.3
		return new SimpleMeterRegistry();
	}

	@Bean
	public Consumer<KStream<Object, String>> process() {

		return input ->
				input.foreach((key, value) -> {
					System.out.println("Key: " + key + " Value: " + value);
				});
	}
}
