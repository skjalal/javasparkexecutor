package com.example;

import kafka.EmbeddedSingleNodeKafkaCluster;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@Slf4j
class JavaSparkExecutorTest {

    @RegisterExtension
    public EmbeddedSingleNodeKafkaCluster embeddedSingleNodeKafkaCluster = new EmbeddedSingleNodeKafkaCluster();

    private JavaSparkExecutor javaSparkExecutor;

    @BeforeEach
    void setUp() {
        javaSparkExecutor = new JavaSparkExecutor();
    }

    @Test
    void testMain() {
        embeddedSingleNodeKafkaCluster.createTopic("TestTopic");
        log.info("Topic Created...!");
        JavaSparkExecutor.main(new String[]{});
        Assertions.assertTrue(true);
    }

    @Test
    void testSparkExecute() {
        javaSparkExecutor.sparkExecute();
        Assertions.assertTrue(true);
    }
}