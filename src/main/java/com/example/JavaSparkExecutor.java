package com.example;

import com.example.config.SparkConfiguration;
import com.example.config.SparkProperties;
import com.example.service.SparkJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class JavaSparkExecutor {

    public static void main(String[] args) {
        log.info("This is Java Spark Standalone Application");
        ConfigurableApplicationContext context = SpringApplication.run(JavaSparkExecutor.class, args);
        SparkJobService sparkJobService = new SparkJobService(context.getBean(SparkProperties.class), context.getBean(SparkConfiguration.class));
        sparkJobService.execute();
    }
}
