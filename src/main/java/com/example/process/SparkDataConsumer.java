package com.example.process;

import com.example.config.SparkProperties;
import com.example.model.Employee;
import com.example.util.SparkConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.streaming.StreamingQuery;

import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class SparkDataConsumer implements Consumer<Dataset<Employee>> {

    private final SparkProperties sparkProperties;

    public SparkDataConsumer(SparkProperties sparkProperties) {
        this.sparkProperties = sparkProperties;
    }

    @Override
    public void accept(Dataset<Employee> employeeDataset) {
        log.info("Data Processing ...!");
        StreamingQuery streamingQuery = employeeDataset
                .writeStream()
                .format("console")
                .option(SparkConstant.CHECKPOINT_LOCATION, SparkConstant.SPARK_KAFKA_CHECKPOINT_LOCATION)
                .outputMode(SparkConstant.UPDATE_MODE)
                .start();
        try {
            long duration = Optional
                    .of(Long.parseLong(sparkProperties.getPropertyValue(SparkConstant.SPARK_STREAMING_QUERY_TIME_OUT_MS)))
                    .orElse(Long.MAX_VALUE);

            streamingQuery.awaitTermination(duration);
        } catch (Exception e) {
            log.error("Error while consuming Data from Kafka");
        }
    }
}
