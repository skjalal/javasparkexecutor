package com.example.config;

import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SparkConfigurationTest {

    @Autowired
    SparkConfiguration sparkConfiguration;

    @Test
    void testGetSparkSession() {
        SparkSession sparkSession = sparkConfiguration.getSparkSession();
        Assertions.assertNotNull(sparkSession);
        sparkSession.close();
    }
}