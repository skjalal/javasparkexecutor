package com.example.util;

public interface SparkConstant {

    String SPARK_DEFAULT_APP_NAME = "SparkAppDefault";
    String LOCAL = "local";
    String SPARK_MASTER = "spark.master";
    String SPARK_APP_NAME = "spark.appName";
    String SPARK_DEFAULT_PROPERTY_DRIVER_MEMORY = "spark.defaultProperty.driver.memory";
    String SPARK_DEFAULT_PROPERTY_DRIVER_MAX_RESULT_SIZE = "spark.defaultProperty.driver.maxResultSize";
    String SPARK_DEFAULT_PROPERTY_DRIVER_CORES = "spark.defaultProperty.driver.cores";
    String SPARK_DEFAULT_PROPERTY_LOCAL_DIR = "spark.defaultProperty.local.dir";
    String SPARK_SQL_SHUFFLE_PARTITIONS = "spark.sql.shuffle.partitions";
    String SPARK_SQL_WAREHOUSE_DIR = "spark.sql.warehouse.dir";
    String SPARK_STREAMING_DURATION = "spark.streaming.duration";
    String SPARK_STREAMING_QUERY_TIME_OUT_MS = "spark.streaming.query.timeOut";
    String DESERIALIZE = "deserialize";
    String DESERIALIZE_AS_VALUE = "deserialize(value) as value";
    String CAST_KEY_AS_STRING_AS_KEY = "cast(key as string) as key";
    String KEY = "key";
    String JSON_VALUE_STAR = "json_value.*";
    String JSON_VALUE = "json_value";
    String VALUE = "value";
    String EMPLOYEES_VIEW = "employees_view";
    String SQL = "select * from employees_view";
    String CHECKPOINT_LOCATION = "checkPointLocation";
    String UPDATE_MODE = "update";
    String SPARK_KAFKA_CHECKPOINT_LOCATION = "spark.kafka.checkPointLocation";
}
