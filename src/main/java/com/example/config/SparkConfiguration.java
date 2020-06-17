package com.example.config;

import com.example.util.SparkConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import java.util.Optional;

@Slf4j
public class SparkConfiguration {

    private final SparkProperties sparkProperties;
    private SparkSession sparkSession;

    public SparkConfiguration(SparkProperties sparkProperties) {
        this.sparkProperties = sparkProperties;
    }

    public SparkSession getSparkSession() {
        log.debug("Initializing Spark Session");
        String sparkAppName = Optional.ofNullable(sparkProperties.getPropertyValue(SparkConstant.SPARK_APP_NAME)).orElse(sparkProperties.getPropertyValue(SparkConstant.SPARK_DEFAULT_APP_NAME));
        if (sparkSession == null) {
            sparkSession = SparkSession.builder().appName(sparkAppName).config(sparkConf()).getOrCreate();
        }
        return sparkSession;
    }

    private SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf();
        sparkConf.set(SparkConstant.SPARK_MASTER, sparkProperties.getPropertyValue(SparkConstant.SPARK_MASTER));
        sparkConf.set(SparkConstant.SPARK_LOCAL_DIR, sparkProperties.getPropertyValue(SparkConstant.SPARK_LOCAL_DIR));
        sparkConf.set(SparkConstant.SPARK_DRIVER_CORES, sparkProperties.getPropertyValue(SparkConstant.SPARK_DRIVER_CORES));
        sparkConf.set(SparkConstant.SPARK_DRIVER_MAX_RESULT_SIZE, sparkProperties.getPropertyValue(SparkConstant.SPARK_DRIVER_MAX_RESULT_SIZE));
        sparkConf.set(SparkConstant.SPARK_DRIVER_MEMORY, sparkProperties.getPropertyValue(SparkConstant.SPARK_DRIVER_MEMORY));
        sparkConf.set(SparkConstant.SPARK_SQL_SHUFFLE_PARTITIONS, sparkProperties.getPropertyValue(SparkConstant.SPARK_SQL_SHUFFLE_PARTITIONS));
        sparkConf.set(SparkConstant.SPARK_SQL_WAREHOUSE_DIR, sparkProperties.getPropertyValue(SparkConstant.SPARK_SQL_WAREHOUSE_DIR));
        return sparkConf;
    }
}
