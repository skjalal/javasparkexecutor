package com.example.util;

public interface KafkaConstant {

    String FORMAT = "kafka";
    String KAFKA_SERVERS = "kafka.bootstrap.servers";
    String KAFKA_TOPIC = "kafka.topic";
    String GROUP_ID = "group.id";
    String GROUP_ID_VALUE = "spark-kafka-consumer";
    String SUBSCRIBE = "subscribe";
    String POLL_TIME_OUT_MS = "kafka.consumer.pollTimeOut";
    String STARTING_OFFSET = "startingOffsets";
    String FAIL_ON_DATA_LOSS = "failOnDataLoss";
    String FAIL_ON_DATA_LOSS_VALUE = "kafka.consumer.failOnDataLoss";
    String KAFKA_SCHEMA_REGISTRY_URL = "kafka.schema.registry.url";
}
