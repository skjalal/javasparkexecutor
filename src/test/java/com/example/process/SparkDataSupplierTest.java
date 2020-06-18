package com.example.process;

import com.example.config.SparkConfiguration;
import com.example.config.SparkProperties;
import kafka.EmbeddedSingleNodeKafkaCluster;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SparkDataSupplierTest {

    @RegisterExtension
    public EmbeddedSingleNodeKafkaCluster cluster = new EmbeddedSingleNodeKafkaCluster();

    private static final String TOPIC_NAME = "TestTopic";
    private SparkDataSupplier sparkDataSupplier;
    private SparkSession sparkSession;

    @Autowired
    SparkProperties sparkProperties;

    @Autowired
    SparkConfiguration sparkConfiguration;

    @BeforeEach
    void setUp() {
        cluster.produceSampleRecords(TOPIC_NAME);
        sparkSession = sparkConfiguration.getSparkSession();
        sparkDataSupplier = new SparkDataSupplier(sparkSession, sparkProperties);
    }

    @Test
    void testGet() {
        Dataset<Row> rowDataset = sparkDataSupplier.get();
        Assertions.assertNotNull(rowDataset);
    }

    @AfterEach
    void tearDown() {
        sparkConfiguration.closeSparkSession();
    }
}