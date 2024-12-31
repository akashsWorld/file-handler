package com.cromxt.routeservice.config;

import com.cromxt.kafka.BucketInformation;
import com.cromxt.kafka.BucketsUpdateRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class BucketsUpdateKafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, BucketsUpdateRequest> bucketsUpdateConsumerFactory(Environment environment) {
        String bootstrapServers = environment.getProperty("ROUTE_SERVICE_BOOTSTRAP_SERVERS", String.class);
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, String.format("%s-%s", UUID.randomUUID(), UUID.randomUUID()));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.cromxt.kafka.BucketsUpdateRequest"); // Ensure correct type
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BucketsUpdateRequest> bucketsUpdateKafkaListenerContainerFactory(Environment environment) {
        ConcurrentKafkaListenerContainerFactory<String, BucketsUpdateRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(bucketsUpdateConsumerFactory(environment));
        return factory;
    }
}