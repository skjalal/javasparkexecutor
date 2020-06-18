package com.example.service;

import com.example.config.SparkConfiguration;
import com.example.config.SparkProperties;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import kafka.EmbeddedSingleNodeKafkaCluster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SparkJobServiceTest {

    @RegisterExtension
    public EmbeddedSingleNodeKafkaCluster cluster = new EmbeddedSingleNodeKafkaCluster();

    private static final String TOPIC_NAME = "TestTopic";

    private SparkJobService sparkJobService;

    @Autowired
    SparkProperties sparkProperties;

    @Autowired
    SparkConfiguration sparkConfiguration;

    @BeforeEach
    void setUp() throws IOException, RestClientException {
        cluster.produceSampleRecords(TOPIC_NAME);
        sparkJobService = Mockito.spy(new SparkJobService(sparkProperties, sparkConfiguration));
    }

    @Test
    void testExecute() {
        sparkJobService.execute();
        Mockito.verify(sparkJobService).execute();
    }
}