package com.example.util;

public final class KafkaConstant {

    public static final String FORMAT = "kafka";
    public static final String KAFKA_SERVERS = "kafka.bootstrap.servers";
    public static final String KAFKA_TOPIC = "kafka.topic";
    public static final String GROUP_ID = "group.id";
    public static final String GROUP_ID_VALUE = "spark-kafka-consumer";
    public static final String SUBSCRIBE = "subscribe";
    public static final String POLL_TIME_OUT_MS = "kafkaConsumer.pollTimeoutMs";
    public static final String POLL_TIME_OUT_MS_VALUE = "kafka.consumer.pollTimeOut";
    public static final String STARTING_OFFSET = "startingOffsets";
    public static final String STARTING_OFFSET_VALUE = "kafka.consumer.startingOffsets";
    public static final String FAIL_ON_DATA_LOSS = "failOnDataLoss";
    public static final String FAIL_ON_DATA_LOSS_VALUE = "kafka.consumer.failOnDataLoss";
    public static final String KAFKA_SCHEMA_REGISTRY_URL = "kafka.schema.registry.url";

    private KafkaConstant() {
        throw new UnsupportedOperationException();
    }
}
