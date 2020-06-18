package com.example;

import com.example.config.SparkConfiguration;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import kafka.EmbeddedSingleNodeKafkaCluster;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class JavaSparkExecutorTest {

    @RegisterExtension
    public EmbeddedSingleNodeKafkaCluster cluster = new EmbeddedSingleNodeKafkaCluster();

    private static final String TOPIC_NAME = "TestTopic";

    @Autowired
    SparkConfiguration sparkConfiguration;

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

    @AfterEach
    void tearDown() {
        sparkConfiguration.closeSparkSession();
    }
}