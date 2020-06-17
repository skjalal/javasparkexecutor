package com.example.config;

import com.example.util.SparkConstant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SparkPropertiesTest {

    @Test
    void testGetPropertyValue() {
        SparkProperties sparkProperties = new SparkProperties("Test", "LocalTest");
        String sparkAppName = sparkProperties.getPropertyValue(SparkConstant.SPARK_APP_NAME);
        assertEquals("javasparkexecutor", sparkAppName);
    }
}