package com.example.process;

import com.example.config.SparkConfiguration;
import com.example.config.SparkProperties;
import com.example.model.Employee;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import kafka.EmbeddedSingleNodeKafkaCluster;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SparkDataHandlerTest {

    @RegisterExtension
    public EmbeddedSingleNodeKafkaCluster cluster = new EmbeddedSingleNodeKafkaCluster();

    private static final String TOPIC_NAME = "TestTopic";
    private SparkDataHandler sparkDataHandler;
    private SparkDataSupplier sparkDataSupplier;

    @Autowired
    SparkProperties sparkProperties;

    @Autowired
    SparkConfiguration sparkConfiguration;

    @BeforeEach
    void setUp() throws IOException, RestClientException {
        cluster.produceSampleRecords(TOPIC_NAME);
        SparkSession sparkSession = sparkConfiguration.getSparkSession();
        sparkDataSupplier = new SparkDataSupplier(sparkSession, sparkProperties);
        sparkDataHandler = new SparkDataHandler(sparkSession, sparkProperties);
    }

    @Test
    void testApply() {
        Dataset<Employee> employeeDataset = sparkDataHandler.apply(sparkDataSupplier.get());
        Assertions.assertNotNull(employeeDataset);
    }

    @AfterEach
    void tearDown() {
        sparkConfiguration.closeSparkSession();
    }
}