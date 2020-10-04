package com.example.process;

import com.example.config.SparkProperties;
import com.example.model.Employee;
import com.example.util.SparkConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.streaming.StreamingQuery;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
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
                .option(SparkConstant.CHECKPOINT_LOCATION, sparkProperties.getPropertyValue(SparkConstant.SPARK_KAFKA_CHECKPOINT_LOCATION))
                .outputMode(SparkConstant.UPDATE_MODE)
                .start();
        try {
            AtomicLong duration = new AtomicLong(Long.MAX_VALUE);
            Optional.ofNullable(sparkProperties.getPropertyValue(SparkConstant.SPARK_STREAMING_QUERY_TIME_OUT_MS)).ifPresent(timeOut -> duration.set(Long.parseLong(timeOut)));
            streamingQuery.awaitTermination(duration.get());
        } catch (Exception e) {
            log.error("Error while consuming Data from Kafka", e);
        }
    }
}
