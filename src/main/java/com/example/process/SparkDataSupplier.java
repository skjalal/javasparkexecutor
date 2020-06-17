package com.example.process;

import com.example.config.SparkProperties;
import com.example.util.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.function.Supplier;

@Slf4j
public class SparkDataSupplier implements Supplier<Dataset<Row>> {

    private final SparkSession sparkSession;
    private final SparkProperties sparkProperties;

    public SparkDataSupplier(SparkSession sparkSession, SparkProperties sparkProperties) {
        this.sparkSession = sparkSession;
        this.sparkProperties = sparkProperties;
    }

    @Override
    public Dataset<Row> get() {
        String kafkaServers = sparkProperties.getPropertyValue(KafkaConstant.KAFKA_SERVERS);
        log.info("Connecting to Kafka Server: {}", kafkaServers);
        String kafkaTopic = sparkProperties.getPropertyValue(KafkaConstant.KAFKA_TOPIC);
        log.info("Starting Streaming On Topic: {}", kafkaTopic);

        Dataset<Row> dataset = sparkSession
                .readStream()
                .format(KafkaConstant.FORMAT)
                .option(KafkaConstant.KAFKA_SERVERS, kafkaServers)
                .option(KafkaConstant.GROUP_ID, KafkaConstant.GROUP_ID_VALUE)
                .option(KafkaConstant.SUBSCRIBE, kafkaTopic)
                .option(KafkaConstant.POLL_TIME_OUT_MS, sparkProperties.getPropertyValue(KafkaConstant.POLL_TIME_OUT_MS_VALUE))
                .option(KafkaConstant.STARTING_OFFSET, sparkProperties.getPropertyValue(KafkaConstant.STARTING_OFFSET_VALUE))
                .option(KafkaConstant.FAIL_ON_DATA_LOSS, sparkProperties.getPropertyValue(KafkaConstant.FAIL_ON_DATA_LOSS_VALUE))
                .load();

        dataset.printSchema();
        return dataset;
    }
}
