package com.example;

import com.example.config.SparkConfiguration;
import com.example.config.SparkProperties;
import com.example.service.SparkJobService;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class JavaSparkExecutor {

    public static void main(String[] args) throws IOException, RestClientException {
        log.info("This is Java Spark Standalone Application");
        ConfigurableApplicationContext context = SpringApplication.run(JavaSparkExecutor.class, args);
        SparkConfiguration sparkConfiguration = context.getBean(SparkConfiguration.class);
        SparkJobService sparkJobService = new SparkJobService(context.getBean(SparkProperties.class), sparkConfiguration);
        sparkJobService.execute();
        sparkConfiguration.closeSparkSession();
    }
}
