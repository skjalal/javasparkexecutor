package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JavaSparkExecutorTest {

    private JavaSparkExecutor javaSparkExecutor;

    @BeforeEach
    void setUp() {
        javaSparkExecutor = new JavaSparkExecutor();
    }

    @Test
    void testMain() {
        JavaSparkExecutor.main(new String[]{});
        Assertions.assertTrue(true);
    }

    @Test
    void testSparkExecute() {
        javaSparkExecutor.sparkExecute();
        Assertions.assertTrue(true);
    }
}