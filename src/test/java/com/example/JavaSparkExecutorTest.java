package com.example;

import kafka.EmbeddedSingleNodeKafkaCluster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class JavaSparkExecutorTest {

    @RegisterExtension
    public EmbeddedSingleNodeKafkaCluster cluster = new EmbeddedSingleNodeKafkaCluster();

    private static final String TOPIC_NAME = "TestTopic";

    @BeforeEach
    void setUp() {
        cluster.createTopic(TOPIC_NAME);
        cluster.produceSampleRecords(TOPIC_NAME);
    }

    @Test
    void testMain() {
        JavaSparkExecutor.main(new String[]{});
        Assertions.assertTrue(true);
    }
}