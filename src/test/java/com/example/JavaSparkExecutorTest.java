package com.example;

import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import kafka.EmbeddedSingleNodeKafkaCluster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.IOException;

class JavaSparkExecutorTest {

    @RegisterExtension
    public EmbeddedSingleNodeKafkaCluster cluster = new EmbeddedSingleNodeKafkaCluster();

    private static final String TOPIC_NAME = "SampleTopic";

    @BeforeEach
    void setUp() {
        cluster.createTopic(TOPIC_NAME);
        cluster.produceSampleRecords(TOPIC_NAME);
    }

    @Test
    void testMain() throws IOException, RestClientException {
        JavaSparkExecutor.main(new String[]{});
        Assertions.assertTrue(true);
    }
}
