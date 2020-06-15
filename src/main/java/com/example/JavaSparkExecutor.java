package com.example;

import com.example.config.SparkConfiguration;
import com.example.config.SparkProperties;
import com.example.service.SparkJobService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaSparkExecutor {

    public static void main(String[] args) {
        log.info("This is Java Spark Standalone Application");
        new JavaSparkExecutor().sparkExecute();
    }

    public void sparkExecute() {
        SparkProperties sparkProperties = new SparkProperties("Spark", "local");
        SparkConfiguration sparkConfiguration = new SparkConfiguration(sparkProperties);
        SparkJobService sparkJobService = new SparkJobService(sparkProperties, sparkConfiguration);
        sparkJobService.execute();
    }
}
