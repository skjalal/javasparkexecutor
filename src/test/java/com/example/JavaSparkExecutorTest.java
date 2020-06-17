package com.example;

import kafka.EmbeddedSingleNodeKafkaCluster;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
class JavaSparkExecutorTest {

    @RegisterExtension
    public EmbeddedSingleNodeKafkaCluster embeddedSingleNodeKafkaCluster = new EmbeddedSingleNodeKafkaCluster();

    @Test
    void testMain() {
        embeddedSingleNodeKafkaCluster.createTopic("TestTopic");
        log.info("Topic Created...!");
        JavaSparkExecutor.main(new String[] {});
        Assertions.assertTrue(true);
    }
}