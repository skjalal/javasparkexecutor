package com.example.config;

import com.example.util.SparkConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SparkPropertiesTest {

    @Autowired
    SparkProperties sparkProperties;

    @Test
    void testGetPropertyValue() {
        String sparkAppName = sparkProperties.getPropertyValue(SparkConstant.SPARK_APP_NAME);
        assertEquals("javasparkexecutor", sparkAppName);
    }
}